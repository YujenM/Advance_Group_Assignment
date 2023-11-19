package com.example.advance_group;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Result {
    @FXML
    private Label qustionattempted;
    @FXML
    private ProgressIndicator correctProgress;

    @FXML
    private Label displaylsignupmessage;

    @FXML
    private Label displaylsignupmessage1;

    @FXML
    private ProgressIndicator wrongProgress;

    private static int loggedInUserId;

    public void getuserid(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
        System.out.println("this is quiz" + loggedInUserId);
        setUserData();
        readResultFromFile();
    }

    public void setUserData() {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String getUserDataQuery = "SELECT UserName, Nationality FROM additionalinfo WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(getUserDataQuery);
            preparedStatement.setInt(1, loggedInUserId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("UserName");
                String nationality = resultSet.getString("Nationality");

                displaylsignupmessage.setText(username);
                displaylsignupmessage1.setText(nationality);

                wrongProgress.setProgress(0.0);

                System.out.println("Username: " + username);
                System.out.println("Nationality: " + nationality);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void readResultFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                if (data.length == 7) {
                    int questionId = Integer.parseInt(data[0]);
                    int correctCount = Integer.parseInt(data[3]);
                    int totalCount = Integer.parseInt(data[4]);
//                    String result = data[5];
                    String verdict = data[6];

                    if (questionId == loggedInUserId) {
                        correctProgress.setProgress((double) correctCount / totalCount);
                        wrongProgress.setProgress(1.0 - correctProgress.getProgress());
//                        displaylsignupmessage.setText("Result: " + result);
                        qustionattempted.setText("Result " + verdict);
//                        qustionattempted.setText("Questions Attempted: " + totalCount);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
