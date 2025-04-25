package dev.tg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rolls {
    HashMap<String, Category> masterCatMap = new HashMap<>(10);
    //Master HashMap that contains all categories (and their Selection Lists)
    String fileLocation = "/filename.exe"; //update with actual file name when added
    public void loadCategoryMap () {

        try {
            InputStream inputFile = getClass().getResourceAsStream(fileLocation);
            assert inputFile != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));
            //Loads Excel File and buffers to improve read speed
            // ********* Double check how this works with an Excel file
            // has only been tested with a comma delimited txt file

            while(bufferedReader.readLine() != null) {
                String line = bufferedReader.readLine();
                String [] splitLine = line.split(",");
                //***double check this, not sure if regex is a comma with an Excel file

                //***add error checking here to ensure index 1 is a number, if not, exit out with an error statement.

                String catName = formatting(splitLine[0]);
                //formats Category name with capitalized first letter, and lowercase body.

                if(!masterCatMap.containsKey(catName)) {
                    //if the Category does not exist in the Map, it is created:
                    masterCatMap.put(catName, new Category(catName, new ArrayList<>()));
                }

                List<Selection> currentCatList = masterCatMap.get(catName).category();
                //extracts category list from hashmap

                for(int i = 2; i < splitLine.length; i++) {
                    currentCatList.add(new Selection(catName, formatting(splitLine[i]), Integer.parseInt(splitLine[1])));
                    //adds each 'Selection' in the current buffered line directly to the Selection List inside the hashmap via mutability
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Main Categories File could not be read: Check fileLocation");
        }
    }

    //place holder class for rolling random lists
    //implements multiple arrays for each list type
    //sets UI text.
    //includes an array that holds String of previous rolls to go back and forth?

    private static String formatting(String string) {
        //ensures text formatting for specific elements are unified

        //***could add error checking here to detect integers, and return an error to acknowledge but do not remove
        //low priority
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1).toLowerCase();
    }

}
