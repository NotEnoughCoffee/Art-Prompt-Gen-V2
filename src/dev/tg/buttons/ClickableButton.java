package dev.tg.buttons;

import dev.tg.Challenge;
import dev.tg.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ClickableButton extends JPanel implements MouseListener {
    public static GUI gui = null;
    protected Rectangle buttonDimensions;
    public boolean enabled;
    public String name;
    protected static String currentChallengeName = Challenge.name;
    public ClickableButton(String name, Rectangle buttonDimensions, boolean enabled) {
        this.name = name;
        this.buttonDimensions = buttonDimensions;
        this.enabled = enabled;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //gets mouse click location and does the click action
        int mouseX = e.getX();
        int mouseY = e.getY();
        if(buttonDimensions.contains(mouseX,mouseY)) {
            System.out.println("Click Event!! You Clicked: " + name);
            doTheThing();
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
    protected void doTheThing() {}
}