package com.example.advance_group;

import com.example.advance_group.Databaseconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


public class LoginSignup {
    @FXML
    private Label displaylogin;
    @FXML
    private TextField getpassword;

    @FXML
    private TextField getusername;

    @FXML
    private Button usercancel;

    @FXML
    private Button userlogin;

    @FXML
    private Button usersignup;
    @FXML
    private Label displaylsignupmessage;
    @FXML
    private Button gotologin;
    @FXML
    private Button signupbtn;
    @FXML
    private TextField signupemail;
    @FXML
    private PasswordField signuppassword;
    @FXML
    private TextField signupusername;

    @FXML
    private TextField getcountry;

    @FXML
    private DatePicker getdob;

    @FXML
    private RadioButton female;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton other;
    @FXML
    private ToggleGroup gender;

//    @FXML
//    void getreadysignup(ActionEvent event) {
//        if(signupusername.getText().isBlank()==false && signupemail.getText().isBlank()==false && signuppassword.getText().isBlank()==false){
//            Validatesignup();
//        }else {
//            displaylsignupmessage.setText("Fill the form");
//        }
//    }

    @FXML
    void getcancel(ActionEvent event) {
        Stage stage=(Stage) usercancel.getScene().getWindow();
        stage.close();
    }

    // go to signup page
    @FXML
    void getsignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) usersignup.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveUserToDatabase(String fullname, String username, String password, String country, LocalDate dob, String gender) {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String insertUserQuery = "INSERT INTO citizendatabase (fullname, username, password, nationality, DOB, gender) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(insertUserQuery);
            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, country);
            preparedStatement.setDate(5, java.sql.Date.valueOf(dob));
            preparedStatement.setString(6, gender);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("User saved to the 'citizendatabase' table.");
            } else {
                System.out.println("Failed to save user.");
            }

            preparedStatement.close();
            connectdb.close();
        } catch (Exception e) {
            e.printStackTrace();
            displaylsignupmessage.setText("Database Error");
        }
    }

    public void Validatesignup() {
        if(signupusername.getText().isBlank()==false && signupemail.getText().isBlank()==false && signuppassword.getText().isBlank()==false){
            try {
                String full_name = signupusername.getText();
                String username = signupemail.getText();
                String password = signuppassword.getText();
                String country = getcountry.getText();
                LocalDate dob = getdob.getValue();
                RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
                String gender = selectedGender.getText();


                if (!full_name.isEmpty() && !username.isEmpty() && !password.isEmpty() && !country.isEmpty() && dob != null) {
                    saveUserToDatabase(full_name, username, password, country, dob, gender);
                    displaylsignupmessage.setText("Signup successful!" );
                } else {
                    displaylsignupmessage.setText("Please fill in all fields.");
                }
            } catch (Exception e) {
                displaylsignupmessage.setText("Database error");
            }

        }else {
            displaylsignupmessage.setText("Fill the form");
        }


    }
// Login
@FXML
void gologin(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) gotologin.getScene().getWindow();
        stage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();

    }
}
    public void validatelogin() {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String verifyLogin = "SELECT count(1) FROM citizendatabase WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(verifyLogin);
            preparedStatement.setString(1, getusername.getText());
            preparedStatement.setString(2, getpassword.getText());
            ResultSet querryresult = preparedStatement.executeQuery();

            while (querryresult.next()) {
                if (querryresult.getInt(1) == 1) {
                    displaylogin.setText("welcome " + getusername.getText());
                } else {
                    displaylogin.setText("Invalid login");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            displaylogin.setText("Database Error");
        }
    }

    @FXML
    void getlogin(ActionEvent event) {
        if(getusername.getText().isBlank()==false && getpassword.getText().isBlank()==false){
            validatelogin();

        }else {
            displaylogin.setText("Enter your UserName and Password");
        }
    }








}
