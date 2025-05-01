package dev.tg.buttons;

import java.awt.*;

public class MainScreenButton extends ClickableButton {

    public MainScreenButton(){
        super("MainScreen", new Rectangle(35,150,730,350), true);
    }
    @Override
    protected void doTheThing() {
        System.out.println("CLICK");
    }
}
