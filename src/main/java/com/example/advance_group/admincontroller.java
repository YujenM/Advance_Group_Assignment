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
import java.util.*;

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
    private Label getmean;

    @FXML
    private Label getmedian;

    @FXML
    private Label getmode;

    @FXML
    private Label getsd;

    @FXML
    private Label getmin;

    @FXML
    private Label getmax;



    @FXML
    private void initialize() {
        System.out.println("Initializing admin");
        populateUsernames();
        setupChoiceBoxListener();
        meanmedian();
    }

    private float calculateMean(int[] values) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return (float) sum / values.length;
    }
    private float calculateMedian(int[] values) {
        Arrays.sort(values);
        if (values.length % 2 == 0) {
            int middle1 = values[values.length / 2 - 1];
            int middle2 = values[values.length / 2];
            return (float) (middle1 + middle2) / 2;
        } else {
            return values[values.length / 2];
        }
    }
    private int calculateMode(int[] values) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int maxCount = 0;
        List<Integer> modes = new ArrayList<>();

        for (int value : values) {
            int count = countMap.getOrDefault(value, 0) + 1;
            countMap.put(value, count);

            if (count > maxCount) {
                maxCount = count;
                modes.clear();
                modes.add(value);
            } else if (count == maxCount) {
                modes.add(value);
            }
        }
        return modes.get(0);
    }


    private float calculateStandardDeviation(int[] values) {
        float mean = calculateMean(values);
        float sumOfSquares = 0;
        for (int value : values) {
            sumOfSquares += Math.pow(value - mean, 2);
        }
        return (float) Math.sqrt(sumOfSquares / values.length);
    }
    private int calculateMinimum(int[] values) {
        Arrays.sort(values);
        return values[0];
    }
    private int calculateMaximum(int[] values) {
        Arrays.sort(values);
        return values[values.length - 1];
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
                if (data.length >= 2) {
                    usernames.add(data[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }
    private void meanmedian() {
        List<Integer> correctCounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    int correctCount = Integer.parseInt(data[2]);
                    correctCounts.add(correctCount);
                }
            }
            // Calculate mean, median, mode, SD, minimum, maximum
            if (!correctCounts.isEmpty()) {
                int[] values = correctCounts.stream().mapToInt(Integer::intValue).toArray();

                float meanValue = calculateMean(values);
                String meanAsString = Float.toString(meanValue);
                getmean.setText(meanAsString);

                float medianValue = calculateMedian(values);
                String medianAsString = Float.toString(medianValue);
                getmedian.setText(medianAsString);

                int modeValue = calculateMode(values);
                String modeAsString = Integer.toString(modeValue);
                getmode.setText(modeAsString);

                float sdValue = calculateStandardDeviation(values);
                String sdAsString = Float.toString(sdValue);
                getsd.setText(sdAsString);

                int minValue = calculateMinimum(values);
                String minAsString = Integer.toString(minValue);
                getmin.setText(minAsString);

                int maxValue = calculateMaximum(values);
                String maxAsString = Integer.toString(maxValue);
                getmax.setText(maxAsString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getResultsAndPrintVerdict() {
        String selectedUsername = getusername.getValue();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Advance Programming assignment\\jfx\\Advance_Group_Assignment\\src\\main\\java\\com\\example\\advance_group\\result\\result.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String username = data[1].trim();

                    if (username.equals(selectedUsername)) {
                        String questionAttemptedStr = data[5].trim();
                        int correctCount = Integer.parseInt(data[2]);
                        int wrongcount = Integer.parseInt(data[3]);
                        float correctpercent=((float) correctCount/20);
                        float wrongpercent=((float) wrongcount/20);

                        System.out.println(correctpercent);
                        System.out.println(wrongpercent);

                        System.out.println("ID: " + data[0] + ", result " + questionAttemptedStr + ", Correct Answer: "
                                + correctCount + ", Wrong Answer: " + data[3]);
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
