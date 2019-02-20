package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.net.URL;
import java.util.ResourceBundle;


public class ControlPaneController implements Initializable {



    @FXML
    private Button PrevButton;

    @FXML
    private Button PlayPauseButton;

    @FXML
    private Button StopButton;

    @FXML
    private Button NextButton;

    @FXML
    private Label VolumeLabel;

    @FXML
    private Slider VolumeSlider;

    @FXML
    private Label SongTimeLabel;

    @FXML
    private Slider SongTimeSlider;


    public Button getStopButton() {
        return StopButton;
    }

    public void setStopButton(Button stopButton) {
        StopButton = stopButton;    }

    public Button getPrevButton() {
        return PrevButton;
    }

    public void setPrevButton(Button prevButton) {
        PrevButton = prevButton;    }

    public Button getPlayPauseButton() {
        return PlayPauseButton;
    }

    public void setPlayPauseButton(Button playPauseButton) {
        PlayPauseButton = playPauseButton;
    }

    public Button getNextButton() {
        return NextButton;
    }

    public void setNextButton(Button nextButton) {
        NextButton = nextButton;
    }

    public Label getVolumeLabel() {
        return VolumeLabel;
    }

    public void setVolumeLabel(Label volumeLabel) {
        VolumeLabel = volumeLabel;
    }

    public Slider getVolumeSlider() {
        return VolumeSlider;
    }

    public void setVolumeSlider(Slider volumeSlider) {
        VolumeSlider = volumeSlider;
    }

    public Label getSongTimeLabel() {
        return SongTimeLabel;
    }

    public void setSongTimeLabel(Label songTimeLabel) {
        SongTimeLabel = songTimeLabel;
    }

    public Slider getSongTimeSlider() {
        return SongTimeSlider;
    }

    public void setSongTimeSlider(Slider songTimeSlider) {
        SongTimeSlider = songTimeSlider;
    }

    @FXML
    void playStopAction(ActionEvent event) {
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
