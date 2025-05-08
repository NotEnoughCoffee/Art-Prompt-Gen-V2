package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainScreenButton extends ClickableButton {
    public MainScreenButton(){
        super("MainScreen", true);
        xPoints = new int[]{80, 720, 720, 80};
        yPoints = new int[]{212, 212, 442, 442};
        buttonDimensions = new Polygon(xPoints,yPoints, 4);
        key1 = KeyEvent.VK_BACK_SPACE;
        key2 = KeyEvent.VK_Q;
    }
    @Override
    protected void doTheThing() {
        System.out.println("CLICK");
        //gui.refresh();
    }
}
