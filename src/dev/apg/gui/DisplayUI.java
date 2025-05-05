package dev.apg.gui;

import dev.apg.Challenge;
import dev.apg.utility.FileLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayUI extends FileLoader {

    //BASIC SETUP//
    public static String promptButtonText;
    public String uiText;
    public int x, y;

    //GUI SETUP//
    GUI gui;
    Graphics2D g2D;
    Font ariel_32, ariel_28, ariel_72;
    BufferedImage background;
    final String backgroundImageFileLocation = "/graphics/90s_Background_V2.png";

    //INITIALIZATION//
    public DisplayUI(GUI gui) {
        this.gui = gui;
        this.background = loadImage(backgroundImageFileLocation);
        ariel_32 = new Font("Ariel", Font.BOLD, 32);
        ariel_28 = new Font("Ariel", Font.BOLD, 28);
        ariel_72 = new Font("Ariel", Font.BOLD, 72);
    }

    //DRAW METHODS//
    public void draw(Graphics2D g2d) {
        //Ran when repaint -> paintComponent is called on GUI class
        //This method redraws the screen, reloads the 'background' image and loaded text elements
        this.g2D = g2d;
        g2d.drawImage(background,0,0,null);

        //MARKED FOR UPDATE//
        setFont(g2d,ariel_32);
        g2d.setColor(Color.black);
        //                 //

        drawText();
    }
    public void drawText() {
        resetUIText();
        setUIText(gui.rollText);
        drawButtonText();

        //REMOVE//
        //Below is a temporary solution to display roll text until challenge class changes are implemented
        String[] text = uiText.split(";;");
        y = 150 + (350/2) - ((text.length -1) * 39)/2;
        for(String s : text) {
            getFontCenterX(s);
            g2D.drawString(s, x, y); //use this to draw multiple strings with a loop
            y += 40;
        }
        //REMOVE//
    }

    //FOR REMOVAL//
    public void drawButtonText() {
        //placeholder method to display button text. needs to be reconfigured after further updates
        //String[] buttonText = new String[] {Challenge.name,"Save","Back","Roll","Forward","Settings"} ;
        //buttonText[0] = Challenge.name;
        setFont(g2D,ariel_72);
        getFontCenterX(Challenge.name);
        y = 140; //35 + (105/2) +72/4;
        g2D.drawString(Challenge.name,x,y);
//        y = 510 + 55/2 + 28/4;
//        setFont(g2D,ariel_28);
//        for(int i = 1; i < buttonText.length; i++) {
//            int length = (int) g2D.getFontMetrics().getStringBounds(buttonText[i], g2D).getWidth();
//            x = 130/2 - (length/2);
//            x += 35;
//            x += (150* (i-1));
//            g2D.drawString(buttonText[i],x,y);
//
//        }
        setFont(g2D,ariel_32);
    }

    //FORMATTING//
    public void resetUIText() {
        uiText = "";
        x = 0;
        y = 0;
    }
    public void setFont(Graphics2D g2d, Font font) {
        g2d.setFont(font);
    }
    public void setUIText (String string) {
        this.uiText = string;
    }
    public void getFontCenterX(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = GUI.screenWidth / 2 - length / 2;
    } //sets X coordinate so text is centered on screen based on the strings width

    //UNIMPLEMENTED METHODS//

    //public void getFontCenterXLocal (String text) {} - method for finding the center of X for localized boxes

    //public void wordWrap (String[] line) {} -> potentially make on FormatText class


}
