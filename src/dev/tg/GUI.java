package dev.tg;

import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

    //screen dimensions in pixels
    final int screenWidth = 800;
    final int screenHeight = 600;
    //GUI Elements
   UIText uiText = new UIText(this); //text displayed when rolls are made. /// Scrap this
    KeyInput buttonPressed = new KeyInput(this);
        //placeholder for button input

    public GUI() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(buttonPressed);
        this.setFocusable(true);
    }

    public void guiSetup() {
        //placeholder for initial GUI element setup
        //load bg image from graphics package
        //button generation and placements
        //music?

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        //calls screen painting method and casts graphic to 2D image.

        uiText.draw(g2D);/// this may need to be removed
        g2D.dispose();
    }


    public void rollChallenge() {
        // something something use this method with the repaint function to redraw the paintComponent method to update the screen with your roll on button press.
        repaint();
    }
}
