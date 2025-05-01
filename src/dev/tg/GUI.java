package dev.tg;

import dev.tg.buttons.*;

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

//    private ClickableButton buildButton(String name, int x, int y, int width, int height) {
//        if(name.contains("SB:")) { //Setting up for future settings page button functions - to be disabled at start
//            return new ClickableButton(name, new Rectangle(x,y,width,height), false);
//        } else {
//            return new ClickableButton(name, new Rectangle(x, y, width, height), true);
//        }
//    }
//    private void addButtons() {
//        //I would like to do this a better way? Loop via loading a file? But not sure how to make that data secure via that method as I do not want users to have access to editing that.
//        buttons = new ArrayList<>(List.of(
//                buildButton("Prompt",35,35,730,105),
//                buildButton("MainScreen",35,150,730,350),
//                buildButton("Save",35,510,130,55),
//                buildButton("Back",185,510,130,55),
//                buildButton("Roll",335,510,130,55),
//                buildButton("Forward",485,510,130,55),
//                buildButton("Settings",635,510,130,55)
//        ));
//    }

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        //calls screen painting method and casts graphic to 2D image.
        displayUI.draw(g2D);
        g2D.dispose();
    }

    private String formatRollTextOutput() {
        StringBuilder string = new StringBuilder();

        for(Selection selection : challenge.currentRollMemory) {
            string.append(selection.Category()).append(": ").append(selection.name()).append("\n");
        }
        return String.valueOf(string);
    }

    public void refresh() {
        rollText = formatRollTextOutput();
        repaint();
    }
    public void rollChallenge() {
        // something something use this method with the repaint function to redraw the paintComponent method to update the screen with your roll on button press.
        //
        challenge.runChallenge();
        rollText = formatRollTextOutput();
//        System.out.println(rollText);
//        displayUI.uiText = rollText;
        repaint();
    }


}
