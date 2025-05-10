package dev.apg.gui.buttons;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainScreenButton extends ClickableButton {

    Rectangle upperHalf;
    Rectangle lowerHalf;
    public MainScreenButton(){
        super("MainScreen", true);
        xPoints = new int[]{80, 720, 720, 80};
        yPoints = new int[]{212, 212, 442, 442};
        buttonDimensions = new Polygon(xPoints,yPoints, 4);
        key1 = KeyEvent.VK_EQUALS;
        key2 = KeyEvent.VK_MINUS;
        upperHalf = new Rectangle(xPoints[0],yPoints[0],xPoints[1]-xPoints[0],(yPoints[2]-yPoints[1])/2);
        lowerHalf = new Rectangle(xPoints[0],yPoints[0] + (yPoints[2]-yPoints[1])/2,xPoints[1]-xPoints[0],(yPoints[2]-yPoints[1])/2);
    }
    @Override
    protected void doTheThing(int x, int y) {
        if(y == -1500 || upperHalf.contains(x,y)) { //Volume Up// Click upper half or press key1
            gui.changeVolume(true);
        }else if (y == -2000 || lowerHalf.contains(x,y)) { //Volume Down// Click lower half or press key2
            gui.changeVolume(false);
        }

        //gui.refresh();
    }
}
