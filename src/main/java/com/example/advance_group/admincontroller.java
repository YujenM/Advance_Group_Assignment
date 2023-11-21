package com.example.advance_group;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class admincontroller {
    @FXML
    private Label qustionattempted;
    @FXML
    private ProgressIndicator correctProgress;
    @FXML
    private ProgressIndicator wrongProgress;
    @FXML
    private Button logout;
    @FXML
    private ChoiceBox<String> getusername;



    @FXML
    private void initialize() {
        System.out.println("Initializing admin");
        populateUsernames();
        setupChoiceBoxListener();
    }

    private void populateUsernames() {
        List<String> usernames = readUsernamesFromFile();
        getusername.getItems().setAll(usernames);
        System.out.println("Usernames: " + usernames);
    }
    private void setupChoiceBoxListener() {
        getusername.setOnAction(event -> {
            getResultsAndPrintVerdict();
        });
    }

    private List<String> readUsernamesFromFile() {
        List<String> usernames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    usernames.add(data[2].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }
    private void getResultsAndPrintVerdict() {
        String selectedUsername = getusername.getValue();

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    String username = data[2].trim();

                    if (username.equals(selectedUsername)) {
                        String questionAttemptedStr = data[6].trim();
                        int correctCount = Integer.parseInt(data[3]);
                        int wrongcount = Integer.parseInt(data[4]);
                        float correctpercent=((float) correctCount/20);
                        float wrongpercent=((float) wrongcount/20);

                        System.out.println(correctpercent);
                        System.out.println(wrongpercent);

                        System.out.println("ID: " + data[0] + ", result " + questionAttemptedStr + ", Correct Answer: "
                                + correctCount + ", Wrong Answer: " + data[4]);

                        qustionattempted.setText(questionAttemptedStr);
                        correctProgress.setProgress(correctpercent);
                        wrongProgress.setProgress(wrongpercent);

                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private List<String> readIdsFromFile() {
        List<String> ids = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    ids.add(data[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
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
