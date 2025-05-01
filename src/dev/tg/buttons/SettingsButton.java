package dev.tg.buttons;

import java.awt.*;

public class SettingsButton extends ClickableButton {

    public SettingsButton(){
        super("Roll", new Rectangle(635,510,130,55), true);
    }
    @Override
    protected void doTheThing() {
        System.out.println("Click");
    }
}
