package dev.apg.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    //determines actions based on buttons pressed

    public boolean forward, back, save, changeChallengeType, nullKey;
    //may need to change boolean to another type later based on how button press actions work (if holding down button makes it scroll endlessly through I do not want that)
    GUI gui;
    public KeyInput(GUI gui) {
        this.gui = gui;
        //constructor connects GUI with key input
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //unknown if this is needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // likely use a switch statement to determine button pressed and action related.
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
