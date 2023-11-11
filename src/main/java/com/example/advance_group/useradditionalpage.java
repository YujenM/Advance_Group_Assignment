package com.example.advance_group;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class useradditionalpage {

    @FXML
    private Label displayfathername;

    @FXML
    private Label displaymothername;

    @FXML
    private Label displayyourname;

    @FXML
    private Label displaynationality;

    @FXML
    private Label displaycitizensip;

    @FXML
    private Label displaydob;

    @FXML
    private  Label displaygender;

    public void initialize() {
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        try {
            Databaseconnection dbConnection = new Databaseconnection();
            Connection connection = dbConnection.getconnection();
            String query = "SELECT Fathername, Mothername, UserName, Nationality, CitizenshipId, Gender, Dob FROM additionalinfo WHERE id = ?";
            int userId = 1;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                displayfathername.setText(resultSet.getString("Fathername"));
                displaymothername.setText(resultSet.getString("Mothername"));
                displayyourname.setText(resultSet.getString("UserName"));
                displaynationality.setText(resultSet.getString("Nationality"));
                displaycitizensip.setText(resultSet.getString("CitizenshipId"));
                displaygender.setText(resultSet.getString("Gender"));
                displaydob.setText(resultSet.getString("Dob"));
            }
            dbConnection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}