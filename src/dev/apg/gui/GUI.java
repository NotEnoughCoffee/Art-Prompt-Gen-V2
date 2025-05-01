package dev.apg.gui;

import dev.apg.Challenge;
import dev.apg.Selection;
import dev.apg.gui.buttons.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JPanel {

    //screen dimensions in pixels
    final int scale = 1;
    final int screenWidth = 800;
    final int screenHeight = 600;
    //GUI Elements
    public DisplayUI displayUI = new DisplayUI(this); //Updates UI with text and images
    List<ClickableButton> buttons;
    KeyInput keyPressed = new KeyInput(this);

    public Challenge challenge = new Challenge();
    String rollText = "";
    public GUI() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyPressed);
        this.setFocusable(true);
        guiSetup();
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
    public void guiSetup() {
        addButtons();
        for(ClickableButton button : buttons) {
            this.addMouseListener(button);
        }
        ClickableButton.gui = this;
        //music?
    }

    public void paintComponent(Graphics g) { //method called when repaint() is used
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        //calls screen painting method and casts graphic to 2D image.
        displayUI.draw(g2D);
        g2D.dispose();
    }

    private String formatRollTextOutput() {
        StringBuilder string = new StringBuilder();
        if(challenge.currentRollMemory == null) {
        challenge.setCurrentMemory();
        }
        for(Selection selection : challenge.currentRollMemory) {
            string.append(selection.Category()).append(": ").append(selection.name()).append(";;");
        }
        return String.valueOf(string);
    }

    public void refresh() {
        rollText = formatRollTextOutput();
        repaint();
    }
    public void rollChallenge() {
        challenge.runChallenge();
        rollText = formatRollTextOutput();
        repaint();
    }


}
