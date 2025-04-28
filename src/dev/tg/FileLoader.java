package dev.tg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    private List<String[]> loadFile(String fileName){
        List<String[]> readLines = new ArrayList<>();
        try {
            InputStream inputFile = getClass().getResourceAsStream(fileName);
            assert inputFile != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));
            int counter = 0;

            while(bufferedReader.readLine() != null) {
                String line = bufferedReader.readLine();
                readLines.add(counter, line.split(","));
                counter++;
            }
            bufferedReader.close();
        }catch(Exception e) {
            System.out.println("Unable to load file: " + fileName);
        }
        return readLines;
    }
}
