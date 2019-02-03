package main.model;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {
    private StringProperty title;
    private DoubleProperty time;
    private StringProperty path;

//Konstruktory
    public Song(String title, Double time, String path) {
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleDoubleProperty(time);
        this.path = new SimpleStringProperty(path);
    }



    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPath() {
        return path.get();
    }

    public StringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public Double getTime() {
        return time.get();
    }

    public DoubleProperty timeProperty() {
        return time;
    }

    public void setTime(Double time) {
        this.time.set(time);
    }


    @Override
    public String toString() {
        return title.get();
    }
}



