package dev.tg;

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
    DisplayUI displayUI = new DisplayUI(this); //Updates UI with text and images
    List<ClickableButton> buttons;
    KeyInput keyPressed = new KeyInput(this);
    Challenge challenge = new Challenge();
    List<String[]> listOfChallenges = new ArrayList<>();

    public GUI() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyPressed);
        this.setFocusable(true);
        guiSetup();
    }

    private ClickableButton buildButton(String name, int x, int y, int width, int height) {
        if(name.contains("SB:")) { //Setting up for future settings page button functions - to be disabled at start
            return new ClickableButton(name, new Rectangle(x,y,width,height), false);
        } else {
            return new ClickableButton(name, new Rectangle(x, y, width, height), true);
        }
    }
    private void addButtons() {
        //I would like to do this a better way? Loop via loading a file? But not sure how to make that data secure via that method as I do not want users to have access to editing that.
        buttons = new ArrayList<>(List.of(
                buildButton("Prompt",35,35,730,105),
                buildButton("MainScreen",35,150,730,350),
                buildButton("Save",35,510,130,55),
                buildButton("Back",185,510,130,55),
                buildButton("Roll",335,510,130,55),
                buildButton("Forward",485,510,130,55),
                buildButton("Settings",635,510,130,55)
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        //calls screen painting method and casts graphic to 2D image.
        displayUI.draw(g2D);
        g2D.dispose();
    }

    public void rollChallenge() {
        // something something use this method with the repaint function to redraw the paintComponent method to update the screen with your roll on button press.
        //
        List<Selection> challengeResults = challenge.runChallenge();
        repaint();
    }
}
