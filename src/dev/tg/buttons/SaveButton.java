package dev.tg.buttons;

import java.awt.*;

public class SaveButton extends ClickableButton {

    public SaveButton(){
        super("Roll", new Rectangle(35,510,130,55), true);
    }
    @Override
    protected void doTheThing() {
        System.out.println("SAVE ME");
    }
}
