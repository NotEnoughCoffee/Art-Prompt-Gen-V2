package dev.tg;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayUI extends FileLoader{
    //class that displays text on top of background image
    GUI gui;
    Graphics2D g2D;
    Font ariel_40, ariel_80;
    BufferedImage background;
    String backgroundImageFileLocation = "/graphics/BackgroundSampleLayout.png";

    //Probably need a button array?

    public boolean messageDisplay = false;
    //is UIText visible?
    public String uiText;
    public static String promptButtonText;
    public int x, y;

    public DisplayUI(GUI gui) {
        this.gui = gui;
        this.background = loadImage(backgroundImageFileLocation);
        ariel_40 = new Font("Ariel", Font.BOLD, 32);
        ariel_80 = new Font("Ariel", Font.BOLD, 72);
    }

    public void draw(Graphics2D g2d) {
        this.g2D = g2d;
        g2d.drawImage(background,0,0,null);
        g2d.setFont(ariel_40);
        g2d.setColor(Color.white);
        drawText();

    }

    public void setUIText (String string) {
        this.uiText = string;
    }
    public void drawText() {
        resetUIText();
        uiText = "Roll text is displayed here";
        //placeholder for roll text method on roll class

        getFontCenterX(uiText);
        y = gui.screenHeight/2; //this centers text vertically, may need to be changed to center text within the top x amount of pixels to leave room for buttons

        g2D.drawString(uiText,x,y);

    }

    public void resetUIText() {
        uiText = "";
        x = 0;
        y = 0;
    }

    public void getFontCenterX(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = gui.screenWidth / 2 - length / 2;
    }


    //make method for centering text on screen (X)
    //make draw method to draw text on screen
    //make reset method to clear drawn text


}
