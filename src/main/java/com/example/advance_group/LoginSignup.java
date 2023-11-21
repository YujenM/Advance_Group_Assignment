package com.example.advance_group;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Optional;

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
    private TextField signupemail;

    @FXML
    private PasswordField signuppassword;

    @FXML
    private TextField signupusername;

//    adminsignup
@FXML
private Label admindisplay;

    @FXML
    private Button adminsignup;

    @FXML
    private PasswordField getadminpassword;

    @FXML
    private TextField getadminusername;

    @FXML
    private Button goadminsignup;

    public int loggedInUserId;

    @FXML
    void getcancel(ActionEvent event) {
        Stage stage = (Stage) usercancel.getScene().getWindow();
        stage.close();
    }

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

    public void gotoAdditionalinfopage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdditionalInformation.fxml"));
            Parent root = loader.load();

            AdditionalInfo additionalInfoController = loader.getController();
            additionalInfoController.initialize(loggedInUserId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) userlogin.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gotouseradtionalinfopage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userinformation.fxml"));
            Parent root = loader.load();
            useradditionalpage controller = loader.getController();
            controller.initialize(loggedInUserId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) userlogin.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void gotoadminpage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminpage.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) userlogin.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void saveUserToDatabase(String fullname, String username, String password) {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String insertUserQuery = "INSERT INTO citizendatabase (fullname, username, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(insertUserQuery);
            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

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
    private void saveadmindatatodatabase(String username, String password){
        Databaseconnection adminconnect=new Databaseconnection();
        Connection adminconnectdb= adminconnect.getconnection();
        String query="INSERT INTO admin (Adminusername,password)VALUES(?,?)";
        try {
            PreparedStatement adminstatement = adminconnectdb.prepareStatement(query);
            adminstatement.setString(1, username);
            adminstatement.setString(2, password);
            int adminrowinserted = adminstatement.executeUpdate();
            if (adminrowinserted > 0) {
                System.out.println("admin signup saved to database");
            } else {
                System.out.println("Failed to save");
            }
            adminstatement.close();
            adminconnectdb.close();
        }catch (Exception e){
            e.printStackTrace();
            admindisplay.setText("Database Error");
        }
    }
    public void Validatesignup() {
        if (signupusername.getText().isBlank() == false && signupemail.getText().isBlank() == false && signuppassword.getText().isBlank() == false) {
            try {
                String full_name = signupusername.getText();
                String username = signupemail.getText();
                String password = signuppassword.getText();

                if (!full_name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    if (isUsernameTaken(full_name)) {
                        displaylsignupmessage.setText(full_name + " already taken.");
                    } else {
                        saveUserToDatabase(full_name, username, password);
                        displaylsignupmessage.setText("Signup successful!");
                    }
                } else {
                    displaylsignupmessage.setText("Please fill in all fields.");
                }
            } catch (Exception e) {
                displaylsignupmessage.setText("Database error");
            }
        } else {
            displaylsignupmessage.setText("Fill the form");
        }
    }
    private boolean verifyPin() {
        TextInputDialog pinDialog = new TextInputDialog();
        pinDialog.setHeaderText("Pin Verification");
        pinDialog.setContentText("Enter the pin:");
        Optional<String> result = pinDialog.showAndWait();
        return result.isPresent() && result.get().equals("911");
    }
    public void validateadminsignup() {
        if (!getadminusername.getText().isBlank() && !getadminpassword.getText().isBlank()) {
            try {
                String adminname = getadminusername.getText();
                String adminpassword = getadminpassword.getText();

                if (!adminname.isEmpty() && !adminpassword.isEmpty()) {
                    if(isUsernameTaken(getadminusername.getText())){
                        admindisplay.setText("Name already taken");
                    }else {
                        if (verifyPin()) {
                            saveadmindatatodatabase(adminname, adminpassword);
                            admindisplay.setText("Admin signup successful");
                        } else {
                            admindisplay.setText("Incorrect pin. Admin signup failed.");
                        }
                    }
                } else {
                    admindisplay.setText("Please fill in all fields");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getadminsignup(){
        validateadminsignup();

    }
    public void goadminsignuppage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminsignup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) goadminsignup.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isUsernameTaken(String full_name) {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String checkUsernameQuery = "SELECT count(1) FROM citizendatabase WHERE fullname = ?";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(checkUsernameQuery);
            preparedStatement.setString(1, full_name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

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
    private  AdditionalInfo AdditionalInfo=new AdditionalInfo();
public void validatelogin() {
    if (getusername != null && getpassword != null) {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String verifyUserLogin = "SELECT id FROM citizendatabase WHERE username = ? AND password = ?";
        String verifyAdminLogin = "SELECT id FROM admin WHERE Adminusername = ? AND password = ?";

        try {
            PreparedStatement userPreparedStatement = connectdb.prepareStatement(verifyUserLogin);
            userPreparedStatement.setString(1, getusername.getText());
            userPreparedStatement.setString(2, getpassword.getText());
            ResultSet userQueryResult = userPreparedStatement.executeQuery();
            if (userQueryResult.next()) {
                loggedInUserId = userQueryResult.getInt("id");
                System.out.println("loggedInUserId: " + loggedInUserId);

                if (isAdditionalInfoFilled(loggedInUserId)) {
                    displaylogin.setText("Welcome back! You have already filled the form.");
                    gotouseradtionalinfopage();
                } else {
                    displaylogin.setText("Welcome " + getusername.getText());
                    AdditionalInfo.setLoggedInUserId(loggedInUserId);
                    gotoAdditionalinfopage();
                }
            } else {
                PreparedStatement adminPreparedStatement = connectdb.prepareStatement(verifyAdminLogin);
                adminPreparedStatement.setString(1, getusername.getText());
                adminPreparedStatement.setString(2, getpassword.getText());
                ResultSet adminQueryResult = adminPreparedStatement.executeQuery();
                if (adminQueryResult.next()) {
                    displaylogin.setText("Welcome Admin: " + getusername.getText());
                    gotoadminpage();
                } else {

                    displaylogin.setText("Invalid login");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            displaylogin.setText("Database Error");
        }
    } else {
        displaylogin.setText("Enter your UserName and Password");
    }
}

    public int getLoggedInUserId() {

        return loggedInUserId;
    }



private Quizpagecontroller quizpagecontroller;
public void setQuizpagecontroller(Quizpagecontroller quizpagecontroller) {
    this.quizpagecontroller = quizpagecontroller;

}

    private boolean isAdditionalInfoFilled(int userId) {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String checkAdditionalInfoQuery = "SELECT id FROM additionalinfo WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(checkAdditionalInfoQuery);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }





    @FXML
    void getlogin(ActionEvent event) {
        if (getusername.getText().isBlank() == false && getpassword.getText().isBlank() == false) {
            validatelogin();

        } else {
            displaylogin.setText("Enter your UserName and Password");
        }
    }
}
