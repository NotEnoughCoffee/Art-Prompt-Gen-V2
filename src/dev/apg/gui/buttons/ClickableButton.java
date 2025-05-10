package dev.apg.gui.buttons;

import dev.apg.gui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ClickableButton extends JPanel implements MouseListener, KeyListener {

    //BASIC SETUP//
    public String name;
    public boolean enabled;
    protected int key1, key2;
    protected int[] xPoints, yPoints;
    public Polygon buttonDimensions;

    public static GUI gui;

    //INITIALIZATION//
    public ClickableButton(String name, boolean enabled) {
        this.name = name;
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
            doTheThing(mouseX,mouseY);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e != null && (e.getKeyCode() == key1 || e.getKeyCode() == key2)) {
            doTheThing();
            if(e.getKeyCode() == key1) {
                doTheThing(0, -1500);
            }else if(e.getKeyCode() == key2) {
                doTheThing(0, -2000);
            }
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
    protected void doTheThing(int x, int y){}
}