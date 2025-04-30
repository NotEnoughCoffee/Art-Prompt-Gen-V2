package dev.tg;

import javax.swing.*;

public class Window {
    //Builds program window
    public static void open() {
        //creates window
        JFrame window = new JFrame();
        window.setTitle("Art Prompt Generator V2");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //add in panels for window content
        GUI gui = new GUI();
        window.add(gui);

        window.pack(); //sets size of window to GUI element
        window.setVisible(true);
    }
}
