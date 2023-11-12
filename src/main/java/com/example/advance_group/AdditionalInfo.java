package com.example.advance_group;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdditionalInfo {

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
    private Button subitform;



    @FXML
    private ChoiceBox<String> selectcountry;



    private Databaseconnection databaseconnection;

    public AdditionalInfo() {
        databaseconnection = new Databaseconnection();
    }
    @FXML
    public void initialize() {
        selectcountry.getItems().addAll("Thailand", "Malaysia", "Singapore");
    }

    public void gotouseradtionalinfopage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinformation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) subitform.getScene().getWindow();
            stage.setScene(scene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void submitinfo() {
        if (gerfathername.getText().isEmpty() || getmothername.getText().isEmpty() ||
                getfullname.getText().isEmpty() || gender.getSelectedToggle() == null ||
                selectcountry.getValue() == null || getcitizenshipid.getText().isEmpty() ||
                getadditonaldob.getValue() == null) {
            System.out.println("Please fill out all fields in the form.");
            return;
        }
        try {
            Connection connectdb = databaseconnection.getconnection();
            String insertInfoQuery = "INSERT INTO additionalinfo (Fathername, Mothername, UserName, Gender, Nationality, CitizenshipId, Dob) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connectdb.prepareStatement(insertInfoQuery);
            preparedStatement.setString(1, gerfathername.getText());
            preparedStatement.setString(2, getmothername.getText());
            preparedStatement.setString(3, getfullname.getText());

            RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
            preparedStatement.setString(4, selectedGender.getText());
            String selectedCountry = selectcountry.getValue();
            preparedStatement.setString(5, selectedCountry);
            preparedStatement.setObject(6, getcitizenshipid.getText());
            preparedStatement.setObject(7, getadditonaldob.getValue());



            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Additional info saved to the 'additionalinfo' table.");
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
}
