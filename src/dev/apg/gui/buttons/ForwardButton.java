package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ForwardButton extends ClickableButton {

    public ForwardButton(){
        super("Forward", true);
        xPoints = new int[]{623, 700, 693, 759, 716, 708, 634};
        yPoints = new int[]{499, 488, 451, 505, 578, 541, 558};
        buttonDimensions = new Polygon(xPoints,yPoints, 7);
        key1 = KeyEvent.VK_RIGHT;
        key2 = KeyEvent.VK_D;
    }
    @Override
    protected void doTheThing() {
        gui.challenge.forwardMemory();
        gui.refresh();
    }
}
