package com.example.advance_group;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestDescriptionPageController {

    private int loggedInUserId;
    public void initialize(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
        System.out.println("this is testdesciption"+loggedInUserId);
    }

    @FXML
    private Button gotoquiz;

    public void gototestpagebtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Testpage.fxml"));
            Parent root = loader.load();
            Quizpagecontroller quizpagecontroller=loader.getController();
            quizpagecontroller.initialize(loggedInUserId);
            Scene scene = new Scene(root);
            Stage stage = (Stage) gotoquiz.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
