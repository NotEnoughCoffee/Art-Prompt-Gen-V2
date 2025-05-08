package dev.apg.utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FileLoader {

    //FILE HANDLERS//
    final protected List<String[]> loadFile(String fileName){
        List<String[]> readLines = new ArrayList<>();
        try {
            InputStream inputFile = getClass().getResourceAsStream(fileName);
            assert inputFile != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));
            int counter = 0;
            String line;

            while((line = bufferedReader.readLine()) != null) {
                for(int i = line.length(); i > 0; i--){
                    if(line.endsWith(",")) {
                        line = line.substring(0, line.length() - 1);
                    }
                }
                if(fileName.contains("csv")){
                    if(line.contains("Category")) {
                        continue;
                        //skips first line of the csv file only
                    }
                }
                readLines.add(counter, line.split(","));
                counter++;
            }
            bufferedReader.close();
        }catch(Exception e) {
            System.out.println("Unable to load file: " + fileName);
        }
        return readLines;
    }
    final protected void saveFile(String fileName, String string) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./res/" + fileName));
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Writing to File:" + fileName);
        }
    }

    //IMAGE HANDLERS//
    final protected BufferedImage loadImage(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filename)));
        }catch (IOException e) {
            System.out.println("Unable to load Image: " + filename);
        }
        return image;
    }
    public static void saveScreen(JPanel gui, Rectangle rectangle) {
        BufferedImage panelSnapshot;
        String date = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        String fileName = date + "_" + time + "_Challenge_Snapshot.png";
        try {
            //grab panel snapshot
            panelSnapshot = new BufferedImage(gui.getWidth(), gui.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2D = panelSnapshot.createGraphics();
            gui.print(g2D);
            g2D.dispose();
        } catch (Exception e) {
            System.out.println("Error with grabbing panel snapshot");
            return;
        }
        //trim snapshot
        panelSnapshot = panelSnapshot.getSubimage(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
        saveImage(panelSnapshot,fileName);

    } //used via save button, has access to rectangle and gui
    private static void saveImage(BufferedImage image, String fileName) {
        String fileLocation = "SavedChallenges/" + fileName;
        try{
            File parentDir = new File(fileLocation).getParentFile();
            if(parentDir.mkdirs()) {
                System.out.println("Parent Directory SavedChallenges was missing and has been created");
            }
            ImageIO.write(image,"png", new File(fileLocation));
        }catch(IOException e) {
            System.out.println("Unable to save image to: " + fileLocation);

        }
    }
}