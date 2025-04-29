package dev.tg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
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
}