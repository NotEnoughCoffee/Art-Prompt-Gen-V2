package dev.tg.buttons;

import java.awt.*;

public class ForwardButton extends ClickableButton {

    public ForwardButton(){
        super("Forward", new Rectangle(485,510,130,55), true);
    }
    @Override
    protected void doTheThing() {
        gui.challenge.forwardMemory();
        gui.refresh();
    }
}
