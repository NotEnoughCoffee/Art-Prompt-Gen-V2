package dev.apg.gui.buttons;

import java.awt.*;

public class RollButton extends ClickableButton {
    public RollButton() {
        super("Roll", new Rectangle(335,510,130,55), true);
    }
    @Override
    protected void doTheThing() {
        ClickableButton.gui.rollChallenge();
    }

}