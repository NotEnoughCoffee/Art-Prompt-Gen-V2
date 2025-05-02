package dev.apg;

import dev.apg.utility.FileLoader;
import dev.apg.utility.FormatText;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Rolls extends FileLoader {

    //BASIC SETUP//
    final private String CatMapFileLocation = "/dataStorage/CategoryMap.csv";
    final private String passFilterFileLocation = "/dataStorage/PassFilter.txt";
    final public String mods = "Modifiers";
    private Long CSVLastModified;
    Random random = new Random();

    //DATA STORAGE MAPS//
    public HashMap<String, Category> masterCatMap = new HashMap<>(10); // Contains all Categories and their Selections
    public HashMap<String,Integer> passFilter = new HashMap<>();

    //INITIALIZATION//
    public Rolls() {
        loadCategoryMap();
        loadPassFilter();
    }

    //FILE LOADING//
    private void loadCategoryMap() {
        List<String[]> catMap = loadFile(CatMapFileLocation);
            for(String[] line : catMap) {
                String catName = FormatText.titleCaps(line[0]); //this grabs category name from first string in row
                int rating = verifyRating(line[1], catName); //this is working
                if (!masterCatMap.containsKey(catName)) {
                    //if the Category does not exist in the Map, it is created:
                    masterCatMap.put(catName, new Category(catName, new ArrayList<>()));
                    if (catName.equalsIgnoreCase(mods)) {
                        //disables modifiers category (this is for special rolls)
                        masterCatMap.get(catName).enabled = false;
                    }
                }
                List<Selection> currentCatList = masterCatMap.get(catName).selections; //extracts category list from hashmap
                for (int i = 2; i < line.length; i++) {
                    currentCatList.add(new Selection(catName, FormatText.titleCaps(line[i]), rating));
                    //adds each 'Selection' in the current buffered line directly to the Selection List inside the hashmap via mutability
                }
            }
        }
    private void loadPassFilter() {
        File file = new File("./res" + CatMapFileLocation);
        List<String[]> filter = loadFile(passFilterFileLocation);
        String[] line = null;
        //you have to specify that split[1] is only on the first item of the first 'line' in the list
        //and an if statement in the loop below to accept the first item of subsequent lines?
        try {
            line = filter.get(0);
        }catch(Exception e) {
            System.out.println("Error Loading Pass Filter");
        }


        if (line != null) {
            if (file.exists()) { //the file exists now
                CSVLastModified = file.lastModified();
                long loggedLastModified = Long.parseLong(line[1]); //this breaks
                if (CSVLastModified == loggedLastModified) {
                    //CSV file modified date matches saved data:
                    filter.forEach( lineF -> {
                        for (int i = 2; i < lineF.length; i += 2) {
                            //starts after LAST_MODIFIED, long,
                            passFilter.put(lineF[i], Integer.valueOf(lineF[i + 1]));
                            //any passes stored in file are loaded into the array.
                        }
                    });
                } else {
                    System.out.println("CSV was modified since last time program closed, resetting pass filter");
                }
            } else {
                System.out.println("Main Categories File does not exist");
            }
        }
    }

    //FILE SAVING//
    private void savePassFilter() {
        StringBuilder saveInfo = new StringBuilder();
        saveInfo.append("LAST_MODIFIED,").append(CSVLastModified).append(",");
        passFilter.forEach( (k,v) -> saveInfo.append(k).append(",").append(v).append(",") );
        saveFile(passFilterFileLocation, String.valueOf(saveInfo));
    }

    //SEARCH FEATURES//
    public Selection searchCatMap(String selectionName) {
        //searches the category map with only a string of the selection name, and returns the selection
        //returns null if not found
        Selection find = null;

        for(Category cat : masterCatMap.values()) {
            if( (find = searchCategory(cat, selectionName)) != null) {
            break;
            }
        }
        if(find == null) {
            System.out.println("Error with searchCatMap, unable to find Selection: " + selectionName);
        }
        return find;
    }
    private Selection searchCategory(Category category, String selectionName) {
        //searches a category with a string name to return the Selection of the same name
        //or returns null if not found
        Selection find = null;
        for(Selection selection : category.selections) {
            if(selectionName.equalsIgnoreCase(selection.name())) {
                find = selection;
                break;
            }
        }
        return find;
    }
    public Category getCategory(Selection selection) {
        return masterCatMap.get(selection.Category());
    }

    //RANDOMIZED ROLLERS//
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
            savePassFilter();
            return randomSelection;
        }
    }
    public Category rollCategory() {
        //Randomly Selects a Category from the Map
        List<String> keys = new ArrayList<>(masterCatMap.keySet());
        Category choice;
        do {
            choice = masterCatMap.get(keys.get(random.nextInt(keys.size())));
        }while(!choice.enabled); //if category is disabled, choice will be rerolled.
        return choice;
    }

    //CATEGORY ENABLE + DISABLE//
    @SuppressWarnings("unused")
    public void allCatEnable() {
        //***** Add checks here for any user defined disable categories.
        masterCatMap.forEach((k,v) -> catEnable(v));
        masterCatMap.get(mods).enabled = false;
    }
    public void catEnable(Category category) {
        category.enabled = true;
    }
    public void catDisable(Category category) {
        category.enabled = false;
    }
    @SuppressWarnings("unused")
    public void catDisable(Selection selection) {
        catDisable(getCategory(selection));
    }

    //FILTERS + CHECKS//
    private int filterCheck(Selection selection, boolean pass) {
        String name = selection.name();
        if(name == null) {
            System.out.println("Selection name = null");
            return -1;
        } else if(passFilter.containsKey(name)) {
            if(passFilter.get(name) >= 10) {
                passFilter.put(name, 0); //resets pass count
                return 1; //Selection is accepted because it was rejected 20+ times
            } else {
                if(pass) {
                    passFilter.put(name, 0); //count reset due to pass.
                    return 1;
                }else {
                    passFilter.put(name, (passFilter.get(name)) + 1); //count incremented due to fail.
                    return 0;
                }
            }
        } else {
            //selection doesn't exist in filter and is added.
            if (pass) {
                passFilter.put(name, 0); //count set to 0 due to immediate pass.
                return 1;
            } else {
                passFilter.put(name, 1);
                return 2;
            }
        }
    }
    private boolean rarityPass(Selection selection) {
        boolean pass;

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

            case 3 -> { // "SUPER_RARE" -> odds = 5/16
                pass = random.nextInt(16) < 5;
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
            case 4 -> { // "MOD_RARE -> odds =
                return random.nextInt(16) < 2;
            }
            default -> throw new IllegalStateException("Unexpected value: " + selection.rarity());
        }
    }
    public boolean rarityPassSuperRare() {
        //Adds weighted logic to return a "Mod Rare" chance to pass
        return rarityPass(new Selection(null,null,4));
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