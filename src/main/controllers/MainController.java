package main.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import javafx.util.Duration;
import main.model.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @FXML
    private ControlPaneController controlPaneController;
    @FXML
    private ControlClosePaneController controlClosePaneController;
    @FXML
    private SongListPaneController songListPaneController;
    @FXML
    private MenuBarController menuBarController;
    private Mp3Player mp3Player;
    private ObservableList<Song> tabelka;
    private Mp3Parser mp3Parser;
    private CloseSerwis closeSerwis;
    private int currentSong = 0;
    private int index;
    boolean onList = false;
    private int movedSongIndex;
    private Thread close;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mp3Player = new Mp3Player();
        configControlPane();
        configTable();
        configClosePane();
        configMenuBar();
    }



//--------------------------------------------------------------------------------------------------------------------------

    private void configMenuBar() {
        MenuItem itemSaveList = menuBarController.getSaveList();
        MenuItem itemOpenList = menuBarController.getOpenList();
        MenuItem itemCloseList = menuBarController.getCloseList();
        MenuItem itemAddSong = menuBarController.getAddSong();
        MenuItem itemDelSong = menuBarController.getDeleteSong();
        MenuItem itemDelAllSongs = menuBarController.getDeleteAllSongs();
        TableView tabela = songListPaneController.getSongTableView();


        itemSaveList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mp3Player.getMp3List().saveListToFile();
            }
        });


        itemOpenList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mp3Player.getMp3List().readListFromFile();
            }
        });


        itemCloseList.setOnAction(Event -> {
            mp3Player.getMp3List().getLista().clear();
            mp3Player.stopSong();
        });


        itemAddSong.setOnAction(Event -> {
            mp3Parser = new Mp3Parser();
            File file = mp3Player.getMp3List().addSongFile();
            mp3Player.getMp3List().addSong(mp3Parser.createMp3SongFromFile(file));
        });


        itemDelSong.setOnAction(Event -> {
            index = tabela.getSelectionModel().getSelectedIndex();
            if(index != -1){
                mp3Player.getMp3List().getLista().remove(index);
            }
        });


        itemDelAllSongs.setOnAction(Event ->
                mp3Player.getMp3List().removedAllSongs()
        );



    }

    private void configMp3Player() {
        mp3Player.getMediaPlayer().setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if(mp3Player.getMp3List().getSongList().size() > index+1){
                    index++;
                    mp3Player.loadSong(index);
                    configSlideBar();
                    configMp3Player();
                } else {
                    mp3Player.getMediaPlayer().stop();
                }
            }
        });
    }

    private void configControlPane (){
        Button playPauseBut = controlPaneController.getPlayPauseButton();
        Button stopBut = controlPaneController.getStopButton();
        Button prevBut = controlPaneController.getPrevButton();
        Button nextBut = controlPaneController.getNextButton();
        Slider volSlid = controlPaneController.getVolumeSlider();
        Slider songTimeSlid = controlPaneController.getSongTimeSlider();
        Label volLab = controlPaneController.getVolumeLabel();
        Label songTimeLab = controlPaneController.getSongTimeLabel();
        TableView tabela = songListPaneController.getSongTableView();




        //bindowanie suwaka głosności z labelem
        volSlid.setValue(30);
        StringProperty labV = volLab.textProperty();
        StringExpression slidV = Bindings.format("Volume: %.0f",  volSlid.valueProperty());
        labV.bind(slidV);


        //dodawanie metody wywołanej w momencie zmiany suwaka głośności
        volSlid.setMin(0);
        volSlid.setMax(100);
        volSlid.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mp3Player.setVolume(newValue.doubleValue()/100);
            }
        });


        //bindowanie suwaka czasu utworu
        StringProperty labS = songTimeLab.textProperty();
        StringExpression slidS = Bindings.format("Time: %.2f",   songTimeSlid.valueProperty()) ;
        labS.bind(slidS);

//



        //przechwytywanie akcji z przycisku PREV za pomocą setOnAction
        prevBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tabela.getSelectionModel().select(tabela.getSelectionModel().getSelectedIndex()-1);
                mp3Player.loadSong(tabela.getSelectionModel().getSelectedIndex());
                configSlideBar();
                playPauseBut.setOpacity(1);
                System.out.println("głośność na " + mp3Player.getMediaPlayer().getVolume());
            }
        });


        //przechwytywanie akcji z przycisku PLAY
        playPauseBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(mp3Player.getMediaPlayer() == null){
                    int index = tabela.getSelectionModel().getSelectedIndex();
                    if (index == -1 ) {
                        index = 0;
                    }
                    mp3Player.loadSong(index);
                    configSlideBar();
                    configMp3Player();
                } else if (mp3Player.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                    mp3Player.getMediaPlayer().pause();
                    playPauseBut.setOpacity(0.3);
                } else {
                    System.out.println("if drugi");
                    mp3Player.getMediaPlayer().play();
                    playPauseBut.setOpacity(1);
                }
            }
        });

        stopBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mp3Player.stopSong();
                playPauseBut.setOpacity(1);
            }
        });


        //przechwytywanie akcji z przycisku NEXT
        nextBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tabela.getSelectionModel().select(tabela.getSelectionModel().getSelectedIndex()+1);
                mp3Player.loadSong(tabela.getSelectionModel().getSelectedIndex());
                configSlideBar();
                configMp3Player();
                playPauseBut.setOpacity(1);
            }
        });
    }






    private void configTable(){
        TableView tabela = songListPaneController.getSongTableView();
        TableColumn lpKol = songListPaneController.getLpColumn();
        TableColumn nameKol = songListPaneController.getNameColumn();
        TableColumn timeKol = songListPaneController.getTimeColumn();


        //podpięcie kolumn pod model danych
        nameKol.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        timeKol.setCellValueFactory(new PropertyValueFactory<Song, String>("time"));
        tabela.setItems(mp3Player.getMp3List().getSongList());
        tabela.setRowFactory(new Callback<TableView, TableRow>() {
            @Override
            public TableRow call(TableView param) {
                return new SongRow();
            }
        });


        //dodanie akcji przeciągnięcia
        tabela.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getDragboard().hasFiles()){
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
        });



        tabela.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(onList){
                    mp3Player.getMp3List().getLista().remove(movedSongIndex);
                }
                event.acceptTransferModes(TransferMode.ANY);
                Dragboard db = event.getDragboard();
                boolean success = false;
                if(db.hasFiles()){
                    success = true;
                    String filePath = null;
                    String fileName = null;
                    mp3Parser = new Mp3Parser();


                    for(File file:db.getFiles()){
                        String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                        if(fileExtension.equalsIgnoreCase("mp3")){
                            mp3Player.getMp3List().addSong(mp3Parser.createMp3SongFromFile(file));
                         }
                    }
                }

                event.setDropCompleted(success);
                tabela.getSelectionModel().select(0);
                event.consume();
            }
        });



        //dodawanie akcji kliknięcia w utwór
        tabela.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 ){

                    index = tabela.getSelectionModel().getSelectedIndex();
                    mp3Player.loadSong(index);
                    mp3Player.getMediaPlayer();
                    configSlideBar();
                    configMp3Player();
                }
            }
        });



    }






    private class SongRow extends TableRow<Song>{
        TableRow thisRow = this;


        public SongRow() {


            //Gdy wykryje przeciąganie myszką
            setOnDragDetected(event -> {
                if (getItem() == null || getItem().getTitle() == "** TOTAL **") {
                    return;
                }

                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(getItem().toString());
                Image photo = new Image("main/resources/next.png");
                dragboard.setDragView(photo);
                dragboard.setContent(content);

                event.consume();
            });


            setOnDragOver(event -> {
                if (event.getGestureSource() != thisRow &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                } else {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            });



            setOnDragEntered(event -> {
                if (event.getGestureSource() != thisRow &&
                        event.getDragboard().hasString()) {
                    setOpacity(0.3);
                }
            });


            setOnDragExited(event -> {
                if (event.getGestureSource() != thisRow &&
                        event.getDragboard().hasString()) {
                    setOpacity(1);
                }
            });


            setOnDragDropped(event -> {

                if (getItem() == null) {
                    return;
                }

                Dragboard db = event.getDragboard();
                boolean success = false;




                if (db.hasString()) {
                    ObservableList<Song> items = getTableView().getItems();
                    ObservableList<String> itemsStr = FXCollections.observableArrayList();
                    for (Song p : items) {
                        itemsStr.add(p.toString());
                    }

                    int draggedIdx = itemsStr.indexOf(db.getString());
                    int thisIdx = itemsStr.indexOf(getItem().toString());
                    System.out.println(draggedIdx + "ciągniety");
                    System.out.println(thisIdx + "indeks komórki nad");


                    if(draggedIdx<thisIdx) {
                        mp3Player.getMp3List().getLista().add(thisIdx, mp3Player.getMp3List().getLista().get(draggedIdx) );
                        mp3Player.getMp3List().getLista().remove(draggedIdx);
                    } else {
                        mp3Player.getMp3List().getLista().add(thisIdx, mp3Player.getMp3List().getLista().get(draggedIdx) );
                        mp3Player.getMp3List().getLista().remove(draggedIdx+1);
                    }
                    success = true;
                }
                event.setDropCompleted(success);

                event.consume();
            });


        }
    }






    private void configSlideBar(){
        Slider songTimeSlid = controlPaneController.getSongTimeSlider();


        mp3Player.getMediaPlayer().setOnReady(new Runnable() {
            @Override
            public void run() {
                songTimeSlid.setMax(mp3Player.getLoadedSongLenght());

            }
        });


        mp3Player.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> arg, Duration oldValue, Duration newValue) {
                songTimeSlid.setValue(newValue.toSeconds());
            }
        });



        songTimeSlid.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (songTimeSlid.isValueChanging()){
                    mp3Player.getMediaPlayer().seek(Duration.seconds(newValue.doubleValue()));
                }
            }
        });

    }


    private void configCloseBar (){
        Slider closeTimeSlider = controlClosePaneController.getTimeMinuteSlider();
        Label lab = controlClosePaneController.getTimeMinuteLabel();

        closeSerwis.getmTime().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lab.setText(String.valueOf(newValue));
            }
        });

    }




    private void configClosePane(){
        Button startBut = controlClosePaneController.getStartButton();
        Label timeMinuteLab = controlClosePaneController.getTimeMinuteLabel();
        Slider mSlid = controlClosePaneController.getTimeMinuteSlider();
        closeSerwis = new CloseSerwis(startBut, mSlid);



        //bindowanie suwaka minutowego z labelem
        mSlid.setValue(0);
        StringProperty labMTime = timeMinuteLab.textProperty();
        StringExpression slidM = Bindings.format("%.0f",  mSlid.valueProperty());
        labMTime.bind(slidM);
        //timeMinuteLab.textProperty().bindBidirectional();
        //mSlid.valueProperty().bindBidirectional();
        //mSlid.valueProperty().bindBidirectional(closeSerwis.getmTime());
        //timeMinuteLab.textProperty().bindBidirectional()



        //przechwytywania akcji z przycisku START
        startBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(startBut.getText().equals("START")){
                    startBut.setText("STOP");
                    mSlid.setDisable(true);

                    int secondToClose =  60*(Integer.parseInt(timeMinuteLab.textProperty().getValue()));
                    CloseSerwis closeSerwis = new CloseSerwis(startBut, mSlid);
                    close = new Thread(closeSerwis);
                    closeSerwis.setCzas(secondToClose);
                    configCloseBar();
                    close.start();


                } else {
                    startBut.setText("START");
                    mSlid.setDisable(false);
                    close.interrupt();

                }
            }
        });
    }

//---------------------------------------------------------------------------------------------------------------------------------
}
