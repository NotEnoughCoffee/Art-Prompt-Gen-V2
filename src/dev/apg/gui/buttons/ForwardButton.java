package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ForwardButton extends ClickableButton {

    public ForwardButton(){
        super("Forward", new Rectangle(485,510,130,55), true);
        button1 = KeyEvent.VK_RIGHT;
        button2 = KeyEvent.VK_D;
    }
    @Override
    protected void doTheThing() {
        gui.challenge.forwardMemory();
        gui.refresh();
    }
}
