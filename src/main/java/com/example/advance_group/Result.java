package com.example.advance_group;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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
    @FXML
    private Button gohome;
    @FXML
    private Button logout;

    @FXML
    private  Label displayCitizen;

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
                if (data.length == 6) {
                    int questionId = Integer.parseInt(data[0]);
                    int correctCount = Integer.parseInt(data[2]);
                    int wrongcount = Integer.parseInt(data[3]);
                    float correctpercent=((float) correctCount/20);
                    float wrongpercent=((float) wrongcount/20);
                    String verdict = data[5];

                    if (questionId == loggedInUserId) {
                        correctProgress.setProgress((correctpercent));
                        wrongProgress.setProgress(wrongpercent);
                        qustionattempted.setText("Result " + verdict);
                        if(Objects.equals(verdict, "pass")){
                            displayCitizen.setText("Congratulations, You are eligible for citizenship for Masathai");
                        }else {
                            displayCitizen.setText("You have failed the citizenship test for Masathai");
                        }
                    }

                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public void gotouseradtionalinfopage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinformation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) gohome.getScene().getWindow();
            useradditionalpage controller = loader.getController();
            controller.initialize(loggedInUserId);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getlogout() {

        Databaseconnection databaseconnection = new Databaseconnection();
        databaseconnection.closeConnection();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
