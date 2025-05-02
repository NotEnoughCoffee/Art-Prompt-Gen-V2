package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainScreenButton extends ClickableButton {

    public MainScreenButton(){
        super("MainScreen", new Rectangle(35,150,730,350), true);
        button1 = KeyEvent.VK_BACK_SPACE;
        button2 = KeyEvent.VK_Q;
    }
    @Override
    protected void doTheThing() {
        System.out.println("CLICK");
        //gui.refresh();
    }
}
