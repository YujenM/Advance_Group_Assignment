package com.example.advance_group;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AdditionalInfo {
    //      Additionalinfofxml
    @FXML
    private Label additionalinfotext;

    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup gender;

    @FXML
    private TextField gerfathername;

    @FXML
    private DatePicker getadditonaldob;

    @FXML
    private TextField getcitizenshipid;

    @FXML
    private TextField getfullname;

    @FXML
    private TextField getmothername;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton other;

    @FXML
    private ChoiceBox<String> selectcountry;

    @FXML
    private Button subitform;

    private Databaseconnection databaseconnection;
    private int loggedInUserId;

    public AdditionalInfo() {

        databaseconnection = new Databaseconnection();
    }

    @FXML
    public void initialize(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
        System.out.println("Initializing AdditionalInfo controller.");
        selectcountry.getItems().addAll("Thailand", "Malaysia", "Singapore");
    }

    public void gotouseradtionalinfopage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinformation.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the FXML file
            useradditionalpage controller = loader.getController();
            controller.initialize(loggedInUserId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) subitform.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void submitinfo(ActionEvent event) {
        try {
            Connection connectdb = databaseconnection.getconnection();
            String insertInfoQuery = "INSERT INTO additionalinfo (id,Fathername, Mothername, UserName, Gender, Nationality, CitizenshipId, Dob) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connectdb.prepareStatement(insertInfoQuery);
            preparedStatement.setInt(1, loggedInUserId);
            preparedStatement.setString(2, gerfathername.getText());
            preparedStatement.setString(3, getmothername.getText());
            preparedStatement.setString(4, getfullname.getText());

            RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
            preparedStatement.setString(5, selectedGender.getText());
            String selectedCountry = selectcountry.getValue();
            preparedStatement.setString(6, selectedCountry);
            preparedStatement.setObject(7, getcitizenshipid.getText());
            preparedStatement.setObject(8, getadditonaldob.getValue());




            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Additional info saved to the 'additionalinfo' table.");
//                fetchDataFromDatabase();
                gotouseradtionalinfopage();

            } else {
                System.out.println("Failed to save additional info.");
            }

            preparedStatement.close();
            databaseconnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();

        }



    }


    public void setLoggedInUserId(int loggedInUserId) {
    }
}
