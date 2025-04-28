package dev.tg;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Rolls extends FileLoader { //aside from testing file input, i think this class is functionally done.
    public HashMap<String, Category> masterCatMap = new HashMap<>(10);
    //Master HashMap that contains all categories (and their Selection Lists)
    String fileLocation = "/dataStorage/CategoryMap.csv"; //Data Storage Location for Category Map
    Long CSVLastModified;
    public HashMap<String,Integer> passFilter = new HashMap<>(); //Logs how many times each selection has been passed
    //needs a method to load from file below
    String passFilterFileLocation = "/dataStorage/PassFilter.txt"; //Data Storage Location for Rarity Passes
    Random random = new Random();
    public Rolls() {
        loadCategoryMap();
        loadPassFilter();
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


                //move above to a separate function. (and buffered reader closer from below)
                //below is added to a for loop and does not need the try/catch right?


                String catName = FormatText.titleCaps(splitLine[0]);
                int rating = verifyRating(splitLine[1], catName);

                if (!masterCatMap.containsKey(catName)) {
                    //if the Category does not exist in the Map, it is created:
                    masterCatMap.put(catName, new Category(catName, new ArrayList<>()));
                }

                List<Selection> currentCatList = masterCatMap.get(catName).selections;
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
    public void loadPassFilter() {
        try {
            InputStream inputFile = getClass().getResourceAsStream(passFilterFileLocation);
            assert inputFile != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));
            File file = new File(fileLocation); //for checking lastModified -> move out of try catch

            while(bufferedReader.readLine() != null) {
                String line = bufferedReader.readLine();
                String[] split = line.split(",");

                //you have to specify that split[1] is only on the first item of the first 'line' in the list
                //and an if statement in the loop below to accept the first item of subsequent lines?

                if(split[1] != null) {
                    if(file.exists()) {
                        CSVLastModified = file.lastModified();
                        long loggedLastModified = Long.parseLong(split[1]);
                        if(CSVLastModified != loggedLastModified) {
                            //CSV file was updated and pass filter remain empty (ie resetting to match current data)
                            break;
                        }
                    } else {
                        System.out.println("Main Categories File does not exist");
                    }
                }
                //data matches, and pass filter will be loaded
                for(int i = 2; i < split.length; i+=2) {
                    //starts after LAST_MODIFIED, long,
                    passFilter.put(split[i], Integer.valueOf(split[i + 1]));
                    //any passes stored in file are loaded into the array.
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Pass Filter unable to load");
        }
    }
    public void savePassFilter() {
        //placeholder for saving the passFilter info on program close
        //unsure how to do that currently
    }
    public Selection rollSelection(Category category) {
        List<Selection> selections = category.selections;
        if (selections.isEmpty()) {
            //checks that list is not empty
            System.out.println("Selection list for: " + category.name + " is empty");
            return new Selection(category.name, "invalid", 1);
        } else {
            Selection randomSelection = null;
            for (int i = 0; i < 5; i++) {
                randomSelection = selections.get(random.nextInt(selections.size()));
                if(rarityPass(randomSelection)) {
                    break;
                }
            } //adds weight to random selection choice, attempts 5 times max.
            return randomSelection;
        }
    }
    public Category rollCategory() {
        //Randomly Selects a Category from the Map
        List<String> keys = new ArrayList<>(masterCatMap.keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        Category choice;
        do {
            choice = masterCatMap.get(randomKey);
        }while(!choice.enabled); //if category is disabled, choice will be rerolled.

        return choice; //disable choice via challenge roll types? to prevent repeats from same category
    }

    public void allCatEnable() {
        //***** Add checks here for any user defined disable categories.
        masterCatMap.forEach((k,v) -> catEnable(v));
    }
    public void catEnable(Category category) {
        category.enabled = true;
    }
    public void catDisable(Category category) {
        category.enabled = false;
    }

    private int filterCheck(Selection selection, boolean pass) {
        String name = selection.name();
        if(passFilter.containsKey(name)) {
            if(passFilter.get(name) >= 20) {
                passFilter.put(name, 0); //resets pass count
                return 1; //Selection is accepted because it was rejected 20+ times
            } else {
                if(pass) {
                    passFilter.put(name, 0); //count reset due to pass.
                }else {
                    passFilter.put(name, (passFilter.get(name)) + 1); //count incremented due to fail.
                }
                return 0;
            }
        } else {
            //selection doesn't exist in filter and is added.
            if (pass) {
                passFilter.put(name, 0); //count set to 0 due to immediate pass.
            } else {
                passFilter.put(name, 1);
                return 2;
            }
        }
        return -77;
    } //untested
    private boolean rarityPass(Selection selection) {
        boolean pass = true;

        switch (selection.rarity()) {
            case 1 -> { return true; }

            case 2 -> { // "RARE" -> odds = 3/5
                pass = random.nextInt(5) < 3;
                int flag = filterCheck(selection, pass);
                if (flag == 1) {
                    return true;
                } else {
                    if(flag < 0) {
                        System.out.println("Error with filterCheck");
                    }
                    return pass;
                }
            }

            case 3 -> { // "SUPER_RARE" -> odds = 1/5
                pass = random.nextInt(15) < 3;
                int flag = filterCheck(selection, pass);
                if (flag == 1) {
                    return true;
                } else {
                    if(flag < 0) {
                        System.out.println("Error with filterCheck");
                    }
                    return pass;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + selection.rarity());
        }
    } //untested
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
    } //untested
}