package dev.apg.gui;

import dev.apg.Challenge;
import dev.apg.utility.FileLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayUI extends FileLoader {
    //class that displays text on top of background image
    GUI gui;
    Graphics2D g2D;
    Font ariel_32, ariel_28, ariel_72;
    BufferedImage background;
    String backgroundImageFileLocation = "/graphics/BackgroundSampleLayout.png";

    public boolean messageDisplay = false;
    //is UIText visible?
    public String uiText;
    public static String promptButtonText;
    public int x, y;

    public DisplayUI(GUI gui) {
        this.gui = gui;
        this.background = loadImage(backgroundImageFileLocation);
        ariel_32 = new Font("Ariel", Font.BOLD, 32);
        ariel_28 = new Font("Ariel", Font.BOLD, 28);
        ariel_72 = new Font("Ariel", Font.BOLD, 72);
    }

    public void draw(Graphics2D g2d) {
        this.g2D = g2d;
        g2d.drawImage(background,0,0,null);
        setFont(g2d,ariel_32);
        g2d.setColor(Color.white);
        drawText();
    }

    public void setUIText (String string) {
        this.uiText = string;
    }

    public void setFont(Graphics2D g2d, Font font) {
        g2d.setFont(font);
    }
    public void drawText() {
        resetUIText();
        setUIText(gui.rollText);

        //getFontCenterX(uiText); //sets x coordinate so text is centered based on pixel width
        //y = gui.screenHeight/2; //sets y coordinate so text is centered vertically

        drawButtonText();
        //Below is a temporary solution to display roll text until challenge class changes are implemented
        String[] text = uiText.split(";;");
        y = 150 + (350/2) - ((text.length -1) * 39)/2;
        for(String s : text) {
            getFontCenterX(s);
            g2D.drawString(s, x, y); //use this to draw multiple strings with a loop
            y += 40;
        }

    }

    public void drawButtonText() {
        //placeholder method to display button text. needs to be reconfigured after further updates
        String[] buttonText = new String[] {Challenge.name,"Save","Back","Roll","Forward","Settings"} ;
        //buttonText[0] = Challenge.name;
        setFont(g2D,ariel_72);
        getFontCenterX(buttonText[0]);
        y = 35 + (105/2) +72/4;
        g2D.drawString(buttonText[0],x,y);
        y = 510 + 55/2 + 28/4;
        setFont(g2D,ariel_28);
        for(int i = 1; i < buttonText.length; i++) {
            int length = (int) g2D.getFontMetrics().getStringBounds(buttonText[i], g2D).getWidth();
            x = 130/2 - (length/2);
            x += 35;
            x += (150* (i-1));
            g2D.drawString(buttonText[i],x,y);

        }
        setFont(g2D,ariel_32);
    }

    public void resetUIText() {
        uiText = "";
        x = 0;
        y = 0;
    }

    public void getFontCenterX(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = GUI.screenWidth / 2 - length / 2;
    }


    //make method for centering text on screen (X)
    //make draw method to draw text on screen
    //make reset method to clear drawn text


}
