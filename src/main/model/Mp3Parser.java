package main.model;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import java.io.*;


public class Mp3Parser {

    public Song createMp3SongFromFile(File file){

        MP3File mp3file = null;
        Double duration = null;

        try {
            mp3file = new MP3File(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        }


        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);



            AudioFile audioFile = AudioFileIO.read(file);
            double dur = audioFile.getAudioHeader().getTrackLength();
            duration =   Math.floor(dur/60) +  dur%60/100 ;

        } catch (CannotReadException | IOException | org.jaudiotagger.tag.TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            e.printStackTrace();
        }

        Song song = new Song(file.getName(), duration, file.getAbsolutePath() );

        return song;
    }
}
