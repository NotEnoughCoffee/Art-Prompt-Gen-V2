package dev.apg.gui;

import dev.apg.Challenge;
import dev.apg.gui.buttons.*;
import dev.apg.utility.SoundHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JPanel {

    //SCREEN SIZE + SCALING//
    @SuppressWarnings("unused")
    public static int scale = 1; //Not implemented yet
    final public static int screenWidth = 800;
    final public static int screenHeight = 600;

    //GUI Elements
    public DisplayUI displayUI = new DisplayUI(this); //GRAPHICS
    List<ClickableButton> buttons; //CLICKABLE BUTTONS + KEY INPUT
    SoundHandler sounds = new SoundHandler();

    //DATA//
    public Challenge challenge = new Challenge();
    String rollText = "";

    //INITIALIZE//
    public GUI() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        guiSetup();
        playMusic(0); //plays bg music.
    }
    public void guiSetup() {
        addButtons();
        for(ClickableButton button : buttons) {
            this.addMouseListener(button);
            this.addKeyListener(button);
        }
        ClickableButton.gui = this;
        //music?
    }
    private void addButtons() {
        buttons = new ArrayList<>(List.of(
                new PromptButton(),
                new MainScreenButton(),
                new SaveButton(),
                new BackButton(),
                new RollButton(),
                new ForwardButton(),
                new SettingsButton()
        ));
    }

    //SCREEN REDRAW METHODS//
    public void rollChallenge() {
        //Runs Challenge through challenge class handlers
        challenge.runChallenge();
        //setRollText();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        displayUI.draw(g2D);
        g2D.dispose();
    }
    public void refresh() {
        repaint();
    }

    //AUDIO HANDLING//
    public void playMusic(int index) {
        sounds.loadSound(index);
        sounds.play();
        sounds.loop();
    }
    public void stopMusic() {
        sounds.stop();
    }
    public void playSoundEffect(int index) {
        sounds.loadSound(index);
        sounds.play();
    }
    public void changeVolume(boolean volumePlusMinus) {
        sounds.volumeControl(volumePlusMinus);
    }
}