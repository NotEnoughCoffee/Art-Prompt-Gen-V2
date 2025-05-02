package dev.apg.gui.buttons;

import dev.apg.Challenge;
import dev.apg.gui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ClickableButton extends JPanel implements MouseListener, KeyListener {
    public static GUI gui = null;
    protected Rectangle buttonDimensions;
    public boolean enabled;
    public String name;
    public int button1, button2;
    protected static String currentChallengeName = Challenge.name;
    public ClickableButton(String name, Rectangle buttonDimensions, boolean enabled) {
        this.name = name;
        this.buttonDimensions = buttonDimensions;
        this.enabled = enabled;
    }

    //MOUSE + KEY EVENTS//
    @Override
    public void mouseClicked(MouseEvent e) {
        //gets mouse click location and does the click action
        int mouseX = e.getX();
        int mouseY = e.getY();
        if(buttonDimensions.contains(mouseX,mouseY)) {
            doTheThing();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e != null && (e.getKeyCode() == button1 || e.getKeyCode() == button2 )) {
            doTheThing();
            System.out.println("You Pressed a button: " + button1 + "/" + button2);
        }
    }

    //UNUSED METHODS//
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

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    //ABSTRACT METHOD FOR OVERRIDING//
    protected void doTheThing() {}
}