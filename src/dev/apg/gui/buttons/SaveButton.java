package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SaveButton extends ClickableButton {

    public SaveButton(){
        super("Roll", new Rectangle(35,510,130,55), true);
        button1 = KeyEvent.VK_DOWN;
        button2 = KeyEvent.VK_S;
    }
    @Override
    protected void doTheThing() {
        System.out.println("SAVE ME");
    }
}
