package main.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import java.io.File;


public class Mp3Player {

    private Media media;
    private MediaPlayer mediaPlayer;
    private Mp3List mp3List;

    public Mp3Player() {
        mp3List = new Mp3List();
    }



    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Media getMedia() {
        return media;
    }

    public Mp3List getMp3List() {
        return mp3List;
    }



    public void playSong(String path){
        if(mediaPlayer != null && (mediaPlayer.getStatus() == Status.READY || mediaPlayer.getStatus() == Status.PAUSED )) {
            mediaPlayer.play();
        }
    }


    public void stopSong() {
        if(mediaPlayer != null && (mediaPlayer.getStatus() == Status.READY || mediaPlayer.getStatus() == Status.PLAYING ))
        mediaPlayer.pause();
    }

    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }

    public void loadSong(int index){
        if(mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING){
            mediaPlayer.stop();
        }

        Song song = getMp3List().getSongList().get(index);
        media = new Media( new File(song.getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

    }


    public double getLoadedSongLenght(){
        if(media != null){
//            double sekundy = media.getDuration().toSeconds();
//            double minuty = Math.floor(sekundy/60) +  sekundy%60/100;
            return media.getDuration().toSeconds()  ;
        } else {
            return 0;
        }
    }

}
