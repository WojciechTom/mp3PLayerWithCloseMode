package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlClosePaneController implements Initializable {

    @FXML
    private Slider TimeMinuteSlider;

    @FXML
    private Slider TimeHourSlider;

    @FXML
    private Label SetTimeMinuteLabel;

    @FXML
    private Label SetTimeHourLabel;

    @FXML
    private Button StartButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Slider getTimeMinuteSlider() {
        return TimeMinuteSlider;
    }

    public void setTimeMinuteSlider(Slider timeMinuteSlider) {
        TimeMinuteSlider = timeMinuteSlider;
    }

    public Slider getTimeHourSlider() {
        return TimeHourSlider;
    }

    public void setTimeHourSlider(Slider timeHourSlider) {
        TimeHourSlider = timeHourSlider;
    }

    public Label getSetTimeMinuteLabel() {
        return SetTimeMinuteLabel;
    }

    public void setSetTimeMinuteLabel(Label setTimeMinuteLabel) {
        SetTimeMinuteLabel = setTimeMinuteLabel;
    }

    public Label getSetTimeHourLabel() {
        return SetTimeHourLabel;
    }

    public void setSetTimeHourLabel(Label setTimeHourLabel) {
        SetTimeHourLabel = setTimeHourLabel;
    }

    public Button getStartButton() {
        return StartButton;
    }

    public void setStartButton(Button startButton) {
        StartButton = startButton;
    }

    @FXML
    void startStopAction(ActionEvent event) {
        if(StartButton.getText().equals("START")){
            StartButton.setText("STOP");
        } else {
            StartButton.setText("START");
        }

    }
}
