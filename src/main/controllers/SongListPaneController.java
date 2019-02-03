package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.model.Song;

import java.net.URL;
import java.util.ResourceBundle;

public class SongListPaneController implements Initializable {



    @FXML
    private TableView<Song> SongTableView;

    @FXML
    private TableColumn<Song, String> LpColumn;

    @FXML
    private TableColumn<Song, String> NameColumn;

    @FXML
    private TableColumn<Song, String> TimeColumn;


    public TableView<Song> getSongTableView() {
        return SongTableView;
    }

    public void setSongTableView(TableView<Song> songTableView) {
        SongTableView = songTableView;
    }

    public TableColumn<Song, String> getLpColumn() {
        return LpColumn;
    }

    public void setLpColumn(TableColumn<Song, String> lpColumn) {
        LpColumn = lpColumn;
    }

    public TableColumn<Song, String> getNameColumn() {
        return NameColumn;
    }

    public void setNameColumn(TableColumn<Song, String> nameColumn) {
        NameColumn = nameColumn;
    }

    public TableColumn<Song, String> getTimeColumn() {
        return TimeColumn;
    }

    public void setTimeColumn(TableColumn<Song, String> timeColumn) {
        TimeColumn = timeColumn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
