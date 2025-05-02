package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RollButton extends ClickableButton {
    public RollButton() {
        super("Roll", new Rectangle(335,510,130,55), true);
        button1 = KeyEvent.VK_SPACE;
        button2 = KeyEvent.VK_ENTER;
    }
    @Override
    protected void doTheThing() {
        ClickableButton.gui.rollChallenge();
    }

}