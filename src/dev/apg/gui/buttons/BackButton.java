package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BackButton extends ClickableButton {
    public BackButton() {
        super("Back", true);
        xPoints = new int[]{43, 110, 105, 189, 181, 99, 94};
        yPoints = new int[]{514, 468, 500, 509, 561, 546, 578};
        buttonDimensions = new Polygon(xPoints,yPoints, 7);
        key1 = KeyEvent.VK_LEFT;
        key2 = KeyEvent.VK_A;
    }
    @Override
    protected void doTheThing() {
        gui.challenge.backwardMemory();
        gui.refresh();
    }
}