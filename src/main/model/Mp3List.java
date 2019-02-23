package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.*;



public class Mp3List {

    private ObservableList<Song> lista;
    private double totalTime = 0;
    private Song end;

    public Mp3List() {
        this.lista = FXCollections.observableArrayList();
        end = new Song("** TOTAL **", 0.0, "");
        lista.add(end);
    }

    public ObservableList<Song> getLista() {
        return lista;
    }

    public void removedAllSongs(){
        lista.clear();
        end = new Song("** TOTAL **", 0.0, "");
        lista.add(end);
    }

    public void addSong(Song song) {

        lista.remove(lista.indexOf(end));
        lista.add(song);

        totalTime = totalTime + song.getTime();
        end = new Song("** TOTAL **", totalTime,"");
        lista.add(end);
    }

    public void fingSongBytitle(String title){
    }

    public ObservableList<Song> getSongList(){
        return lista;
    }



    public File addSongFile(){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        fc.getExtensionFilters().add(extFilter);
        Window stage = null;
        File file = fc.showOpenDialog(stage);
        return file;
    }

    public void saveListToFile(){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);
        Window stage = null;
        String path = fc.showSaveDialog(stage).getPath();

        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bfw = new BufferedWriter(fw);

            for (Song song: lista) {
            bfw.write(song.getTitle()+";"+ song.getTime() +";"+song.getPath());
            bfw.newLine();
            }

            bfw.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void readListFromFile (){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);
        fc.setTitle("Proszę wybrać Listę");
        Window stage = null;
        File file = fc.showOpenDialog(stage);
        String path = file.getPath();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader bfr = new BufferedReader(fr);
            lista.clear();
            String line = null;
            while((line = bfr.readLine()) != null) {
                String[] tab = line.split(";");
                String s1 = tab[0];
                Double d1 = Double.valueOf(tab[1]);
                String s2 = tab[2];
                System.out.println(s2 + "   sciezka");
                addSong(new Song(s1,d1,s2));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
