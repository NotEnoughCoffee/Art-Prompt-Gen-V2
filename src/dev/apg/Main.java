package dev.apg;

import dev.apg.gui.Window;

public class Main {
    public static void main(String[] args) {
        Window.open(); //Runs Entire Program

    }

    //ROLLS CLASS UNIT TESTS
    @SuppressWarnings("unused")
    public static void rolls_Roll_Selection_Test(int loopCount) {
        //Unit Test for Roll Selection Method to check that random selections are being made from randomly selected categories
        Rolls roll = new Rolls();
        for (int i = 0; i < loopCount; i++) {
            System.out.println(roll.rollSelection(roll.rollCategory()));
        }
    }
    @SuppressWarnings("unused")
    static void rolls_Roll_Category_Test(int loopCount) {
        //Unit Test for Roll Category Method to check only 'Enabled' Categories are being selected
        Rolls roll = new Rolls();
        for (int i = 0; i < loopCount; i++) {
            Category cat = roll.rollCategory();
            System.out.println(cat + ": is " + (cat.enabled ? "enabled(true)" : "disabled(false)"));
        }
    }
    @SuppressWarnings("unused")
    static void rolls_Print_Master_Cat_Map_Test() {
        //Unit Test to check the Category Map has loaded from the file correctly.
        //Prints all categories and contents to the console
        Rolls roll = new Rolls();
        roll.masterCatMap.forEach( (k,v) -> System.out.println(v) );
    }
    @SuppressWarnings("unused")
    static void rolls_Search_Cat_Map_Test(String string) {
        //Performs test on Search method to find any selection within the Master Category map with a string input
        //Failure to find search result should also return a console message from the method itself
        Rolls roll = new Rolls();
        Selection searchResult = roll.searchCatMap(string);
        System.out.println("Search with User Defined Parameter:");
        System.out.println(passFailText(searchResult, string));

        searchResult = roll.searchCatMap("Raccoon"); //guaranteed to be found in Category Map
        System.out.println("Search with known selection, expected to Pass:");
        System.out.println(passFailText(searchResult, "Raccoon"));

        searchResult = roll.searchCatMap("rAccoOn");
        System.out.println("Search with known selection with different formatting, expected Pass:");
        System.out.println(passFailText(searchResult, "rAccoOn"));

        searchResult = roll.searchCatMap("R@cc00n");
        System.out.println("Search with misspelled selection, expected Fail:");
        System.out.println(passFailText(searchResult, "R@cc00n"));
    }
    static String passFailText(Selection searchResult, String string) {
        return (searchResult != null) ?
                ("Passed, string was able to find Selection Result: " + searchResult + " with string: " + string) :
                ("Failed, was unable to find matching Search for string: " + string);

    }

    //CHALLENGE CLASS UNIT TESTS
    @SuppressWarnings("unused")
    static void challenge_Run_Default_Challenge_Test() {
        //Unit test on default test method within rolls class. Does not utilize loading test information from a save file
        Challenge challenge = new Challenge();
        challenge.runDefaultChallenge();
        System.out.println(challenge.currentRollMemory);
    }
    //CHALLENGE -> ROLL MEMORY TESTS

    //CATEGORY/SELECTION TESTS


    //UTILITY FEATURE TESTS//

    //FILE LOADER TESTS

    //FORMAT TEXT TESTS


    //GUI FEATURE TESTS//

    //DISPLAY UI TESTS

    //KEY INPUT TESTS

    //CLICKABLE BUTTON TESTS

    //WINDOW TESTS

    //add More Unit Tests
}
