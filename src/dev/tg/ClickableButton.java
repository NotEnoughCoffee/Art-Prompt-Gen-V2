package dev.tg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickableButton extends JPanel implements MouseListener {

    static GUI gui;
    Rectangle buttonDimensions;
    boolean enabled;
    String name;
    static String currentChallengeName = Challenge.name;
    public ClickableButton(String name, Rectangle buttonDimensions, boolean enabled) {
        this.name = name;
        this.buttonDimensions = buttonDimensions;
        this.enabled = enabled;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //will pull in the location of mouse click event with int = e.getX() etc.
        //check between the established rectangle through the constructor -> meaning each button is its own object, created on GUI constructor load
        int mouseX = e.getX();
        int mouseY = e.getY();

        if(buttonDimensions.contains(mouseX,mouseY)) {
            System.out.println("Click Event!! You Clicked: " + name);
            switch(name) {
                case "Prompt" -> {
                    //changes the challenge type by looping through a list of challenge types which alter what things are rolled, and includes user added challenges
                    int max = Challenge.challengesList.size() - 1;
                    if(Challenge.challengeIndex++ >= max) {
                        Challenge.challengeIndex = 0;
                        System.out.println(Challenge.challengeIndex);
                    }
                    DisplayUI.promptButtonText = currentChallengeName;
                    //set currentChallengeName to text that is also being displayed on button
                }
                case "MainScreen" -> {
                    //fluff, maybe replay any animations that were triggered (when implemented)
                }
                case "Save" -> {
                    //Saves current challenge screen
                }
                case "Back" -> {
                    //moves counter back and displays previous challenge
                }
                case "Roll" -> {
                    //rolls challenge based on challenge type (what prompt is currently displaying)
                    //pull challenge name from static string above and plug into the challenge roll function
                    gui.rollChallenge();
                }
                case "Forward" -> {
                    //moves counter forward and displays the next challenge (looping back to start)
                }
                case "Setting" -> {
                    //opens setting menu, disabling all above buttons?
                }
                default -> {
                    System.out.println("Unknown Clickable Button: " + name);
                }

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //placeholder

}
