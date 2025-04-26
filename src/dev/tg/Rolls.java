package dev.tg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Rolls {
    HashMap<String, Category> masterCatMap = new HashMap<>(10);
    //Master HashMap that contains all categories (and their Selection Lists)
    String fileLocation = "/filename.exe"; //update with actual file name when added
    Random random = new Random();
    public void initialSetup() {
        loadCategoryMap();
    }
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
                String catName = formatting(splitLine[0]);
                //formats Category name with capitalized first letter, and lowercase body.
                int rating = verifyRating(splitLine[1],catName);

                if(!masterCatMap.containsKey(catName)) {
                    //if the Category does not exist in the Map, it is created:
                    masterCatMap.put(catName, new Category(catName, new ArrayList<>()));
                }

                List<Selection> currentCatList = masterCatMap.get(catName).selections();
                //extracts category list from hashmap

                for(int i = 2; i < splitLine.length; i++) {
                    currentCatList.add(new Selection(catName, formatting(splitLine[i]), rating));
                    //adds each 'Selection' in the current buffered line directly to the Selection List inside the hashmap via mutability
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Main Categories File could not be read: Check fileLocation and/or filetype");
        }
    }

    public String rollSelection(Category category) {
        List<Selection> selections = category.selections();
        if(selections.isEmpty()) {
            System.out.println("Selection list for: " + category.name() + " is empty");
            return "n/a";
        }

        //add rarity functionality
        //if 1, accept roll, if 2 or 3 use random to determine if roll is accepted, else re-roll.

        return selections.get(random.nextInt(selections.size())).name();

    }

    public Category rollCategory() {
        //Randomly Selects a Category from the Map
        List<String> keys = new ArrayList<>(masterCatMap.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        return masterCatMap.get(randomKey);
    }
    private static int verifyRating(String number, String catName) {
        //Verifies that parsed 'rating' is a number, and is within the range 1-3.
        int rating;

        try {
            rating = Integer.parseInt(number);
        } catch(Exception e) {
            rating = 1;
            System.out.println("Invalid Rating detected for Category: " + catName + "; Rating reset to default");
            //rating reset and user notified of issue, but program will still function.
        }

        if(rating < 1) {
            rating = 1;
        }else if(rating > 3) {
            rating = 3;
        } //ensure rating is within range of 1-3

        return rating;
    }
    private static String formatting(String string) {
        //ensures text formatting for specific elements are unified

        //***could add error checking here to detect integers, and return an error to acknowledge but do not remove
        //***need to include formatting for multi-word category names, split(" ") and run below code on each
        //low priority
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1).toLowerCase();
    } //consider moving to a text formatting class

}
