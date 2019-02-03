package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Mp3List {

    private ObservableList<Song> lista;

    public Mp3List() {
        this.lista = FXCollections.observableArrayList();

//        lista.add(new Song("Rudimental - never let you go", 4.5 , "c/itp"));
//        lista.add(new Song("Era Ameno", 3.3 , "c/itp"));
//        lista.add(new Song("We are the people", 4.3,  "c/itp"));
//        lista.add(new Song("Bałkanica", 5.4, "c/itp"));

    }

    public void addSong(Song song){
        lista.add(song);
    }

    public void fingSongBytitle(String title){

    }

    public ObservableList<Song> getSongList(){
        return lista;
    }


}
