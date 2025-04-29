package dev.tg;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    static Rolls rolls = new Rolls(); //creates Category map, grants access to roll methods
    List<RollMemory> rollMemory = new ArrayList<>(20);
    //Stores previous rolls where [Challenge
    int memoryCounter = 0; //tracks the location for memory to be stored
    int memoryCursor = 0; //tracks user location while browsing previous rolls
    String memoryFileLocation = "/dataStorage/RollMemory.txt"; //for potentially storing roll memory after program close

    public Challenge() {
        for(int i = 0; i < 20; i++) {
            rollMemory.add(i, null);
        } //initiate memory and loads list with null
    }



    public List<Selection> runDefaultChallenge() {
        //Default challenge which picks 2 options, each from a unique enabled category.
        return runChallenge(2, false);
    } // for testing and initial setup // untested

    public List<Selection> runChallenge(int selectionCount, boolean allowCategoryRepeats) {
        //runs the challenge with specified parameters and returns a list of the results
        List<Selection> choices = new ArrayList<>();
        for(int i = 0; i < selectionCount; i++){
            if(allowCategoryRepeats) {
                choices.add(i, roll());
            }else{
                choices.add(i, rollAndDisable());
            }
        }
        addToRollMemory("Daily Challenge", choices); //roll added to memory
        reEnableCats(choices); //re-enables all selected cats (regardless if they were disabled)
        return choices;
    } //untested

    public void reEnableCats(List<Selection> selections) {
        for(Selection s : selections) {
            rolls.catEnable(rolls.masterCatMap.get(s.Category()));
        }
    } //untested
    //use two below methods with a navigation method to display previous or next rolls based on the memory location?
    public void forwardMemory() {
        //moves memory location cursor forwards
        memoryCursor++;
        if(rollMemory.get(memoryCursor) == null) {
            memoryCursor = 0;
        }//brings back to beginning if noting is in the highest available memory slot
        if(memoryCursor == 20) {
            memoryCursor = 0;
        }
    } // navigation not implemented
    public void backwardMemory() {
        //moves memory location cursor backwards
        memoryCursor--;
        if(memoryCursor == -1) {
            for(int i = 19; i > 0; i--) {
                if (rollMemory.get(i) != null) {
                    break;
                }
                memoryCursor = 19;
            }
        }

    } // navigation not implemented
    public void addToRollMemory(String challengeRoll, List<Selection> selections) {
        memoryCounter++;
        if(memoryCounter == 20) {
            memoryCounter = 0;
            //keeps last 20 rolls
        }
        memoryCursor = memoryCounter; //brings the cursor to the current entry that is being saved.
        rollMemory.set(memoryCounter,new RollMemory(challengeRoll,selections));

    } //untested
    public Selection rollAndDisable () {
        //by default, categories remain enabled after a selection is made.
        Selection selection = roll();
        rolls.masterCatMap.get(selection.Category()).enabled = false;
        return selection;
    } //untested
    public Selection roll() {
        //category remains enabled after selection is made.
        return rolls.rollSelection(rolls.rollCategory());
    }
}