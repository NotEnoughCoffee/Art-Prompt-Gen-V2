package dev.tg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Rolls { //aside from testing file input, i think this class is functionally done.
    public HashMap<String, Category> masterCatMap = new HashMap<>(10);
    //Master HashMap that contains all categories (and their Selection Lists)
    String fileLocation = "/filename.exe"; //update with actual file name when added
    Random random = new Random();
    public Rolls() {
        loadCategoryMap();
    }
    public void loadCategoryMap() {

        try {
            InputStream inputFile = getClass().getResourceAsStream(fileLocation);
            assert inputFile != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));
            //Loads Excel File and buffers to improve read speed
            // ********* Double check how this works with an Excel file
            // has only been tested with a comma delimited txt file

            while (bufferedReader.readLine() != null) {
                String line = bufferedReader.readLine();
                String[] splitLine = line.split(",");
                //***double check this, not sure if regex is a comma with an Excel file
                String catName = FormatText.titleCaps(splitLine[0]);
                int rating = verifyRating(splitLine[1], catName);

                if (!masterCatMap.containsKey(catName)) {
                    //if the Category does not exist in the Map, it is created:
                    masterCatMap.put(catName, new Category(catName, new ArrayList<>()));
                }

                List<Selection> currentCatList = masterCatMap.get(catName).selections();
                //extracts category list from hashmap

                for (int i = 2; i < splitLine.length; i++) {
                    currentCatList.add(new Selection(catName, FormatText.titleCaps(splitLine[i]), rating));
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
        if (selections.isEmpty()) {
            //checks that list is not empty
            System.out.println("Selection list for: " + category.name() + " is empty");
            return "n/a";
        } else {
            Selection randomSelection = null;
            for (int i = 0; i < 5; i++) {
                randomSelection = selections.get(random.nextInt(selections.size()));
                if(rarityPass(randomSelection)) {
                    break;
                }
            } //adds weight to random selection choice, attempts 5 times max.
            return randomSelection.name();
        }
    }
    public Category rollCategory() {
        //Randomly Selects a Category from the Map
        List<String> keys = new ArrayList<>(masterCatMap.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        return masterCatMap.get(randomKey);
    }
    private boolean rarityPass(Selection selection) {
        return switch (selection.rarity()) {
            case 1 -> true;
            case 2 -> random.nextInt(5) < 3; // odds = 3/5
            case 3 -> random.nextInt(15) < 3; // odds = 1/5
            default -> throw new IllegalStateException("Unexpected value: " + selection.rarity());
        };
        //***Add future functionality to store rejected options in an external file to keep track of multiple rejections to guarantee it is picked on the 20-25th attempt (and reset). Will need to find a way to check your file's last date updated and 'clear memory if it has changed? (this ensures any update to category file will clear the memory for any long-term issues (unlikely in a program this small))
    }
    private static int verifyRating(String number, String catName) {
        //Verifies that parsed 'rating' is a number, and is within the range 1-3.
        int rating;

        try {
            rating = Integer.parseInt(number);
        } catch(Exception e) {
            rating = 1;
            System.out.println("Invalid Rating detected for Category: " + catName + "; Rating set to normal");
            //rating reset and user notified of issue, but program will still function.
        }

        if(rating < 1) {
            rating = 1;
        }else if(rating > 3) {
            rating = 3;
        } //ensure rating is within range of 1-3

        return rating;
    }

}