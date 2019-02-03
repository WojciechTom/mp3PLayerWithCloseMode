package main.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import javax.xml.crypto.dsig.SignatureMethod;
import java.io.IOException;

public class CloseSerwis implements Runnable {
    private int czas;
    private Button playStopButton;
    private Slider mSlider;
    private DoubleProperty hTime = new SimpleDoubleProperty();
    private DoubleProperty mTime = new SimpleDoubleProperty();


    public CloseSerwis(Button playStopButton, Slider mSlider) {
        this.playStopButton = playStopButton;
        this.mSlider = mSlider;
    }


    public DoubleProperty gethTime() {
        return hTime;
    }



    public DoubleProperty getmTime() {
        return mTime;
    }

    public void setmTime(double mTime) {
        this.mTime.set(mTime);
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public void delayAndShutDown () throws InterruptedException, IOException {


            boolean check = false;
            for (int i = 0 ; i< czas ; i ++) {
                if(playStopButton.getText().equals("STOP")){
                    Thread.sleep(1000);
                    System.out.println("sekunda " + i);
                    System.out.println(playStopButton.getText());
                    System.out.println("czas w zmiennej przed zmianą" + this.getmTime());
                    this.setmTime((0.0+czas-i)/60);
                    System.out.println("czas w zmiennej po zmianie" + this.getmTime());
                    //mSlider.setValue((0.0+czas-(i*10))/250);

                    check = true;
                } else {
                    i = czas;
                    check = false;
                }
            }

        if(check){
            System.out.println("FIRE!!!!!!!!!!!!");
            //  shutDownComputer();
        }


    }


    public void shutDownComputer () throws IOException {

        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("shutdown -s -t 0");
        System.exit(0);

    }


    @Override
    public void run() {
        try {
            delayAndShutDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
