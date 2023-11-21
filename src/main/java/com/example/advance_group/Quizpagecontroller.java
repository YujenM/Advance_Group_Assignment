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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Quizpagecontroller  {
    @FXML
    private Button btnopt1;

    @FXML
    private Button btnopt2;

    @FXML
    private Button btnopt3;

    @FXML
    private Button btnopt4;

    @FXML
    private Label getquestion;
    @FXML
    private Label noofquestionattempted;
    @FXML
    private Label settimer;

    @FXML
    private Button submitbtn;

    @FXML
    private ImageView getflag;


    @FXML
    void getpreviousquestion(ActionEvent event) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayCurrentQuestion();
        }

    }

    @FXML
    void loadNextQuestion(ActionEvent event) {
        if(currentQuestionIndex < questionsList.size() - 1){

            currentQuestionIndex++;
            displayCurrentQuestion();
            answerSelected = false;
            if (currentQuestionIndex >= questionsList.size()) {

            }
        }

    }
    private List<Integer> clickedQuestionsList = new ArrayList<>();
    @FXML
    void option1clicked(ActionEvent event) {
        if (!answerSelected){
            checkAnswer(1);
            loadNextQuestion();
            clickedQuestionsList.add(currentQuestionIndex);
        }

    }

    @FXML
    void option2clicked(ActionEvent event) {
        if (!answerSelected){
            checkAnswer(2);
            loadNextQuestion();
            clickedQuestionsList.add(currentQuestionIndex);

        }

    }

    @FXML
    void option3clicked(ActionEvent event) {
        if (!answerSelected){
            checkAnswer(3);
            loadNextQuestion();
            clickedQuestionsList.add(currentQuestionIndex);

        }

    }

    @FXML
    void option4clicked(ActionEvent event) {
        if (!answerSelected){
            checkAnswer(4);
            loadNextQuestion();
            clickedQuestionsList.add(currentQuestionIndex);

        }

    }
    private int loggedInUserId;
    public void initialize(int loggedInUserId) throws FileNotFoundException {
        this.loggedInUserId = loggedInUserId;
        System.out.println("this is quiz"+loggedInUserId);
        getquestion();
        printClickedQuestions();
    }
    private void printClickedQuestions() {
        System.out.print("Clicked questions: ");
        for (int i = 0; i < clickedQuestionsList.size(); i++) {
            System.out.print(clickedQuestionsList.get(i));
            if (i < clickedQuestionsList.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    public String getcountryname() {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String getCountryNameQuery = "SELECT nationality FROM additionalinfo WHERE id = ?";
        String nationality = "";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(getCountryNameQuery);
            preparedStatement.setInt(1, loggedInUserId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nationality = resultSet.getString("nationality");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nationality;
    }

    public void getquestion() throws FileNotFoundException {
        System.out.println("--------------");
        String nationality = getcountryname();
        System.out.println(nationality);
        File selectedFile = null;
        if (nationality.equals("Malaysia")) {
            String imagePath = "D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\resources\\com\\example\\advance_group\\countryflag\\malaysia.png";
            Image image = new Image(new FileInputStream(imagePath));
            getflag.setImage(image);
            selectedFile = new File("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\questions\\Malaysia.txt");
        } else if (nationality.equals("Thailand")) {
            String imagePath = "D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\resources\\com\\example\\advance_group\\countryflag\\thailand.png";
            Image image = new Image(new FileInputStream(imagePath));
            getflag.setImage(image);
            selectedFile = new File("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\questions\\Thailand.txt");
        } else if (nationality.equals("Singapore")) {
            String imagePath = "D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\resources\\com\\example\\advance_group\\countryflag\\singapore.png";
            Image image = new Image(new FileInputStream(imagePath));
            getflag.setImage(image);
            selectedFile = new File("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\questions\\singapore.txt");
        }
        System.out.println(loggedInUserId);

        if (selectedFile != null && selectedFile.exists()) {
            loadQuestions(selectedFile);
            questionsAttemptedCounter = 1;
            startTimer();
        } else {
            System.err.println("File does not exist: " + (selectedFile != null ? selectedFile.getAbsolutePath() : "null"));
        }
    }


    @FXML
    void submitButtonClicked(ActionEvent event) {
        if (currentQuestionIndex == questionsList.size() - 1) {
            printCorrectAnswers();
            printWrongAnswers();
            stopTimer();
            System.out.println("Current Logged In User ID: " + loggedInUserId);
            System.out.println("hello");
            writeResultsToFile("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt", loggedInUserId);
            gotoresultpage();
        }
    }

    public void gotoresultpage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Result.fxml"));
            Parent root = loader.load();
            Result result=loader.getController();
            result.getuserid(loggedInUserId);
            Scene scene = new Scene(root);
            Stage stage = (Stage) submitbtn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private void writeResultsToFile(String filePath, int userId) {
    if (questionsList != null) {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                String userData = getUserData(userId);

                if (!userData.isEmpty()) {
                    String userResult = userId + "," + userData + "," +
                            correctAnswersCounter + "," + wrongAnswersList.size() + "," +
                            questionsAttemptedCounter + "/" + questionsList.size() + "," +
                            (calculatePassOrFail() ? "pass" : "fail") + "\n";

                    writer.write(userResult);
                    System.out.println("User data appended to file: " + userData);
                } else {
                    System.out.println("Error: User data is empty.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.err.println("Questions list is null.");
    }
}





    public String getUserData(int userId) {
        Databaseconnection connectnow = new Databaseconnection();
        Connection connectdb = connectnow.getconnection();
        String getUserDataQuery = "SELECT id, username FROM citizendatabase WHERE id = ?";
        String userData = "";

        try {
            PreparedStatement preparedStatement = connectdb.prepareStatement(getUserDataQuery);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                userData = id +","+  username;
                System.out.println("User Data: " + userData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userData;
    }


    private int questionsAttemptedCounter;





    private List<String[]> questionsList;
    private int currentQuestionIndex;
    public void loadQuestions(File selectedFile) {
        try {
            if (selectedFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                questionsList = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    String[] questionData = line.split(",");
                    questionsList.add(questionData);
                }
                currentQuestionIndex = 0;
                displayCurrentQuestion();
            } else {
                System.err.println("File does not exist: " + selectedFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void displayCurrentQuestion() {
        if (questionsList != null && currentQuestionIndex >= 0 && currentQuestionIndex < questionsList.size()) {
            String[] currentQuestion = questionsList.get(currentQuestionIndex);
            if (currentQuestion.length >= 5) {
                getquestion.setText(currentQuestion[0]);
                btnopt1.setText(currentQuestion[1]);
                btnopt2.setText(currentQuestion[2]);
                btnopt3.setText(currentQuestion[3]);
                btnopt4.setText(currentQuestion[4]);
            } else {
                System.err.println("Question data is incomplete for question " + currentQuestionIndex);
            }
        } else {
            System.err.println("Invalid question index: " + currentQuestionIndex);
        }
    }
    private boolean answerSelected = false;
    public void loadNextQuestion() {
        if(currentQuestionIndex < questionsList.size() - 1){

            currentQuestionIndex++;
            displayCurrentQuestion();
            answerSelected = false;
            if (currentQuestionIndex >= questionsList.size()) {

            }
        }
    }
    private  int correctAnswersCounter;
    private boolean calculatePassOrFail() {
        int passThreshold = 8;
        return correctAnswersCounter >= passThreshold;
    }
    private List<Integer> wrongAnswersList = new ArrayList<>();
    public void checkAnswer(int selectedOption) {
        if (questionsList != null && currentQuestionIndex >= 0 && currentQuestionIndex < questionsList.size()) {
            String[] currentQuestion = questionsList.get(currentQuestionIndex);
            if (currentQuestion.length >= 6) {
                String answerString = currentQuestion[5].trim();
                String answerIndexString = answerString.substring(0, 1);
                try {
                    int correctAnswerIndex = Integer.parseInt(answerIndexString);
                    if (selectedOption == correctAnswerIndex) {
                        correctAnswersCounter++;
                    } else {
                        wrongAnswersList.add(currentQuestionIndex + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing answer index: " + answerIndexString);
                }
            }
            if (currentQuestionIndex < questionsList.size() - 1) {
                questionsAttemptedCounter++;
                updateAttemptedQuestionsLabel();
            }
            answerSelected = true;
        }
    }

    private void updateAttemptedQuestionsLabel() {
        noofquestionattempted.setText("Questions Attempted: " + questionsAttemptedCounter);
    }
    public void printCorrectAnswers() {

        System.out.println("Number of correct answers: " + correctAnswersCounter);
        String results;
        if (correctAnswersCounter <8) {
            results="fail";
            System.out.println(results);

        }else{
            results="pass";
            System.out.println(results);
        }
    }
    private void printWrongAnswers() {
        int totalQuestions = questionsList.size();
        int wrongAnswers = wrongAnswersList.size();
        System.out.println("Number of wrong answers: " + wrongAnswers);
        System.out.println("Questions attempted: " + questionsAttemptedCounter + "/" + totalQuestions);
        System.out.print("Wrong answers: ");
        for (int i = 0; i < wrongAnswersList.size(); i++) {
            System.out.print(wrongAnswersList.get(i));
            if (i < wrongAnswersList.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    private Timeline timeline;
    private int timeSeconds = 5 * 60;
    public void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds--;
            updateTimerLabel();
            if (timeSeconds <= 0) {
                timeline.stop();
                disableAllButtons();
                gotoresultpage();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void updateTimerLabel() {
        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        settimer.setText(String.format("%02d:%02d", minutes, seconds));
//        settimer.setText(String.format("%02d", seconds));

    }
    public void stopTimer() {
        timeline.stop();
    }
    private void disableAllButtons() {
        btnopt1.setDisable(true);
        btnopt2.setDisable(true);
        btnopt3.setDisable(true);
        btnopt4.setDisable(true);
        submitbtn.setDisable(false);
    }
}
