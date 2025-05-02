package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingsButton extends ClickableButton {

    public SettingsButton(){
        super("Roll", new Rectangle(635,510,130,55), true);
        button1 = KeyEvent.VK_E;
        button2 = KeyEvent.VK_ESCAPE;
    }
    @Override
    protected void doTheThing() {
        System.out.println("Click");
    }
}
