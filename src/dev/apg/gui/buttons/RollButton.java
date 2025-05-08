package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RollButton extends ClickableButton {
    public RollButton() {
        super("Roll", true);
        xPoints = new int[]{277, 540, 530, 509, 510, 280, 264, 282};
        yPoints = new int[]{453, 488, 539, 540, 561, 577, 482, 483};
        buttonDimensions = new Polygon(xPoints,yPoints, 8);
        key1 = KeyEvent.VK_SPACE;
        key2 = KeyEvent.VK_ENTER;
    }
    @Override
    protected void doTheThing() {
        ClickableButton.gui.rollChallenge();
    }

}