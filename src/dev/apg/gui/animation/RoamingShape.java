package dev.apg.gui.animation;

import dev.apg.gui.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RoamingShape {

    private GUI gui;
    private Timer timer;
    Random random = new Random();
    int x, y;
    Point currentLocation, destinationLocation, savedLocation;
    int moveCounter = 0;
    public Rectangle rectangle;
    public RoamingShape(GUI gui, Rectangle rectangle) {
        this.gui = gui;

        this.rectangle = rectangle;
        getNextXY();
        savedLocation = new Point(x,y);
        this.rectangle.x = savedLocation.x;
        this.rectangle.y = savedLocation.y;
        setNextLocation();
        timer = new Timer(0, e -> {
            moveTowardsDestination();
            gui.refresh();

        });
    }


    //MOVEMENT//
    public void moveTowardsDestination() {
        int xTravel = (destinationLocation.x - savedLocation.x) / 40; // will be appropriately positive or negative.
        int yTravel = (destinationLocation.y - savedLocation.y) / 40;

        rectangle.x += xTravel;
        rectangle.y += yTravel;

        moveCounter++;
        if(moveCounter >= 40) { //reached destination = sets currentLocation to destination


            //reset counter to 0 and set new destination
            moveCounter = 0;
            setNextLocation();
        }


    }

    //GETTING NEW LOCATION//
    private void setNextLocation() {
        if(destinationLocation != null) {
            savedLocation = new Point(rectangle.x,rectangle.y);
        }
        getNextXY();
        destinationLocation = new Point(x,y); // x and y are exclusively used here to be the next location
    }
    private void getNextXY() {
        int formerX = x;
        int formerY = y;
        this.x = getNextX();
        this.y = getNextY();
        int xDifference = formerX - x;
        int yDifference = formerY - y;

        while((xDifference > -20 && xDifference < 20) || (xDifference < -200 || xDifference > 200)) { //makes sure X/Y is at least +/-20 pixels away or re-rolls
            this.x = getNextX();
            xDifference = formerX - x;
        }
        while((yDifference > -20 && yDifference < 20) || (yDifference < -200 || yDifference > 200)) {
            this.y = getNextY();
            yDifference = formerY - y;
        }
    }
    private int getNextX() {
        return random.nextInt(25,GUI.screenWidth - 25 - rectangle.width);
    }
    private int getNextY() {
        return random.nextInt(25, GUI.screenHeight - 25 - rectangle.height);
    }
}