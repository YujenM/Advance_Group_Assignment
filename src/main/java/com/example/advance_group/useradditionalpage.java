package com.example.advance_group;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class useradditionalpage{

    @FXML
    private Label displayfathername;

    @FXML
    private Label displaymothername;

    @FXML
    private Label displayyourname;

    @FXML
    private Label displaygender;

    @FXML
    private Label displaynationality;

    @FXML
    private Label displaycitizensip;

    @FXML
    private Label displaydob;


    private int loggedInUserId;

    public void initialize(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
        System.out.println(loggedInUserId);
        fetchDataFromDatabase();
    }


    @FXML
    private Button logout;
    @FXML
    private Button taketestbtn;

    private void fetchDataFromDatabase() {
        try {
            Databaseconnection databaseconnection = new Databaseconnection();
            Connection connectdb = databaseconnection.getconnection();

            String selectInfoQuery = "SELECT * FROM additionalinfo WHERE id = ?";
            PreparedStatement preparedStatement = connectdb.prepareStatement(selectInfoQuery);
            preparedStatement.setInt(1, loggedInUserId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                displayfathername.setText(resultSet.getString("Fathername"));
                displaymothername.setText(resultSet.getString("Mothername"));
                displayyourname.setText(resultSet.getString("UserName"));
                displaygender.setText(resultSet.getString("Gender"));
                displaynationality.setText(resultSet.getString("Nationality"));
                displaycitizensip.setText(resultSet.getString("CitizenshipId"));
                displaydob.setText(resultSet.getString("Dob"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
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
    public void gototestdescriptionpage(ActionEvent event
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("testdescription.fxml"));
            System.out.println(loggedInUserId);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) taketestbtn.getScene().getWindow();
            TestDescriptionPageController testDescriptionPageController=loader.getController();
            testDescriptionPageController.initialize(loggedInUserId);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
