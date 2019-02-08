package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    @FXML
    private MenuItem AddSong;

    @FXML
    private MenuItem DeleteSong;

    @FXML
    private MenuItem DeleteAllSongs;

    @FXML
    private MenuItem SaveList;

    @FXML
    private MenuItem OpenList;

    @FXML
    private MenuItem CloseList;

    public MenuItem getAddSong() {
        return AddSong;
    }

    public void setAddSong(MenuItem addSong) {
        AddSong = addSong;
    }

    public MenuItem getDeleteSong() {
        return DeleteSong;
    }

    public void setDeleteSong(MenuItem deleteSong) {
        DeleteSong = deleteSong;
    }

    public MenuItem getDeleteAllSongs() {
        return DeleteAllSongs;
    }

    public void setDeleteAllSongs(MenuItem deleteAllSongs) {
        DeleteAllSongs = deleteAllSongs;
    }

    public MenuItem getSaveList() {
        return SaveList;
    }

    public void setSaveList(MenuItem saveList) {
        SaveList = saveList;
    }

    public MenuItem getOpenList() {
        return OpenList;
    }

    public void setOpenList(MenuItem openList) {
        OpenList = openList;
    }

    public MenuItem getCloseList() {
        return CloseList;
    }

    public void setCloseList(MenuItem closeList) {
        CloseList = closeList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
