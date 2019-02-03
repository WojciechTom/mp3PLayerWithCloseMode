package main.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;
import main.model.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @FXML
    private ControlPaneController controlPaneController;
    @FXML
    private ControlClosePaneController controlClosePaneController;
    @FXML
    private SongListPaneController songListPaneController;
    private Mp3Player mp3Player;
    private ObservableList<Song> tabelka;
    private Mp3Parser mp3Parser;
    private int currentSong = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mp3Player = new Mp3Player();
        configControlPane();
        configTable();
        configClosePane();
    }

//--------------------------------------------------------------------------------------------------------------------------


    private void configControlPane (){
        Button playBut = controlPaneController.getPlayStopButton();
        Button prevBut = controlPaneController.getPrevButton();
        Button nextBut = controlPaneController.getNextButton();
        Slider volSlid = controlPaneController.getVolumeSlider();
        Slider songTimeSlid = controlPaneController.getSongTimeSlider();
        Label volLab = controlPaneController.getVolumeLabel();
        Label songTimeLab = controlPaneController.getSongTimeLabel();
        TableView tabela = songListPaneController.getSongTableView();




        //bindowanie suwaka głosności
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
            }
        });


        //przechwytywanie akcji z przycisku PLAY wersja skrócona
        playBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = tabela.getSelectionModel().getSelectedIndex();
                System.out.println("to wskazuje index "  + index);
                if (index == -1 ) {
                    index = 0;
                }
                mp3Player.loadSong(index);
                configSlideBar();
            }
        });


        //przechwytywanie akcji z przycisku NEXT
        nextBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tabela.getSelectionModel().select(tabela.getSelectionModel().getSelectedIndex()+1);
                mp3Player.loadSong(tabela.getSelectionModel().getSelectedIndex());
                configSlideBar();
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

                                            //        nameKol.setCellFactory(column -> {
                                            //         return new TableCell<Song, String>(){
                                            //             @Override
                                            //             protected void updateItem(String item, boolean empty){
                                            //                 super.updateItem(item, empty);
                                            //
                                            //                 if (item == null || empty) {
                                            //                     setText(null);
                                            //                     setStyle("");
                                            //                 } else {
                                            //                     setText(item);
                                            //                 }
                                            //
                                            //                 Song piosenka = getTableView().getItems().get(getIndex());
                                            //
                                            //                 if(piosenka.getTitle().equals("Dua Lipa - Be The One")){
                                            //                     setTextFill(Color.BLUE);
                                            //                     setStyle( "-fx-background-color: yellow");
                                            //                 } else {
                                            //                 }
                                            //             }
                                            //
                                            //         };
                                            //        }
                                            //        );




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
                event.consume();
            }
        });



        //dodawanie akcji kliknięcia w utwór
        tabela.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 ){

                    int index = tabela.getSelectionModel().getSelectedIndex();
                    mp3Player.loadSong(index);
                    configSlideBar();
                }
            }
        });



    }




    private void configSlideBar(){
        Slider songTimeSlid = controlPaneController.getSongTimeSlider();


        //
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




    private void configClosePane(){
        Button startBut = controlClosePaneController.getStartButton();
        Label timeMinuteLab = controlClosePaneController.getSetTimeMinuteLabel();
        Label timeHourLab = controlClosePaneController.getSetTimeHourLabel();
        Slider mSlid = controlClosePaneController.getTimeMinuteSlider();
        Slider hSlid = controlClosePaneController.getTimeHourSlider();
        CloseSerwis closeSerwis = new CloseSerwis(startBut, mSlid);


        //bindowanie suwaka minutowego z labelem
        mSlid.setValue(0);
        StringProperty labMTime = timeMinuteLab.textProperty();
        StringExpression slidM = Bindings.format("%.0f",  mSlid.valueProperty());
        labMTime.bind(slidM);
        //timeMinuteLab.textProperty().bindBidirectional();
        //mSlid.valueProperty().bindBidirectional();
        //mSlid.valueProperty().bindBidirectional(closeSerwis.getmTime());
        //timeMinuteLab.textProperty().bindBidirectional()


        //bindowanie suwaka godzinowego z labelem
        hSlid.setValue(0);
        StringProperty labHTime = timeHourLab.textProperty();
        StringExpression slidH = Bindings.format("%.0f",  hSlid.valueProperty());
        labHTime.bind(slidH);
        hSlid.valueProperty().bindBidirectional(closeSerwis.gethTime());



        //przechwytywania akcji z przycisku START
        startBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(startBut.getText().equals("START")){
                    startBut.setText("STOP");
                    mSlid.setDisable(true);
                    hSlid.setDisable(true);

                    int secondToClose =  60*(Integer.parseInt(timeMinuteLab.textProperty().getValue()) + 60*Integer.parseInt(timeHourLab.textProperty().getValue()));
                    CloseSerwis closeSerwis = new CloseSerwis(startBut, mSlid);
                    Thread close = new Thread(closeSerwis);
                    closeSerwis.setCzas(secondToClose);
                    close.start();


                } else {
                    startBut.setText("START");
                    mSlid.setDisable(false);
                    hSlid.setDisable(false);

                }
            }
        });
    }



//---------------------------------------------------------------------------------------------------------------------------------


}
