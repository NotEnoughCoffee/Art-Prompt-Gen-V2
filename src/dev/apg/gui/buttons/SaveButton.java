package dev.apg.gui.buttons;

import dev.apg.utility.FileLoader;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SaveButton extends ClickableButton {
    public SaveButton(){
        super("Save",  true);
        xPoints = new int[]{712, 769, 777, 698};
        yPoints = new int[]{ 78,  60, 160, 171};
        buttonDimensions = new Polygon(xPoints,yPoints, 4);
        key1 = KeyEvent.VK_DOWN;
        key2 = KeyEvent.VK_S;
    }
    @Override
    protected void doTheThing() {
        if(gui.challenge.currentRollMemory != null) {
            FileLoader.saveScreen(gui, new Rectangle(35, 150, 730, 350));
        }
    }
}
