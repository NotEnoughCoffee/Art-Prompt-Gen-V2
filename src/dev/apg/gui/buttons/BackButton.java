package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BackButton extends ClickableButton {
    public BackButton() {
        super("Back", new Rectangle(185, 510, 130, 55), true);
        button1 = KeyEvent.VK_LEFT;
        button2 = KeyEvent.VK_A;
    }
    @Override
    protected void doTheThing() {
        gui.challenge.backwardMemory();
        gui.refresh();
    }
}