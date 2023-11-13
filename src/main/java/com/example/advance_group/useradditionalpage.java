package com.example.advance_group;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        fetchDataFromDatabase();
    }

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
            databaseconnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
