package dev.apg.utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SoundHandler {
    Clip clip;
    List<InputStream> soundInputStreams = new ArrayList<>();
    FloatControl gainControl;



    public SoundHandler() {
        //REPLACE with a file loaded with text from memory

        //Music//
        soundInputStreams.add(getClass().getClassLoader().getResourceAsStream("./audio/piano_dreams.wav"));
            //add more later
        //Effects//
            //add later
    }
    public void loadSound(int index) {
        try {
//            InputStream fileInput = getClass().getClassLoader().getResourceAsStream("./audio/piano_dreams.wav");
            assert soundInputStreams.get(index) != null;
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundInputStreams.get(index)); // this does not work
            clip = AudioSystem.getClip();
            clip.open(audioInput);
        } catch (Exception e) {
            System.out.println("Unable to load sound: " + soundInputStreams.get(index).toString());
            System.out.println(e);
        }
    }
    public void play() {
        if(clip != null) {
            clip.start();
            //Set Initial Volume// ->
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-14.0f);
        }else {
            System.out.println("Unable to Play Sound");
        }
    }
    public void volumeControl (boolean volumePlusMinus) {
        //Increments volume by +-5db. + = true || - = false
        if(gainControl != null) {
            if(volumePlusMinus && gainControl.getValue() < -4) {
                gainControl.setValue(Math.round(gainControl.getValue() + 2));
                System.out.println("Volume = " + gainControl.getValue());
            }else if(!volumePlusMinus && gainControl.getValue() > -50.00f) {
                gainControl.setValue(Math.round(gainControl.getValue() - 2));
                System.out.println("Volume = " + gainControl.getValue());
            }
        }
    }
    public void loop() {
        if(clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }else {
            System.out.println("Unable to Loop Sound");
        }
    }
    public void stop() {
        clip.stop();
    }


}
