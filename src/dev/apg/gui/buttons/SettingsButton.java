package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingsButton extends ClickableButton {

    public SettingsButton(){
        super("Roll", true);
        xPoints = new int[]{24, 78, 107,  32};
        yPoints = new int[]{80, 62, 146, 182};
        buttonDimensions = new Polygon(xPoints,yPoints, 4);
        key1 = KeyEvent.VK_E;
        key2 = KeyEvent.VK_ESCAPE;
    }
    @Override
    protected void doTheThing() {
        System.out.println("Click");
    }
}
