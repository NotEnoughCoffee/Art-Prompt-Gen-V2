package dev.tg.buttons;

import java.awt.*;

public class BackButton extends ClickableButton {
    public BackButton() {
        super("Back", new Rectangle(185,510,130,55), true);
    }
    @Override
    protected void doTheThing() {
        gui.challenge.backwardMemory();
        gui.refresh();
    }
}