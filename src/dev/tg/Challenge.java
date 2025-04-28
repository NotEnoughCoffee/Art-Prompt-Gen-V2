package dev.tg;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    static Rolls rolls = new Rolls(); //creates Category map, grants access to roll methods
    List<RollMemory> rollMemory = new ArrayList<>(20);
    //Stores previous rolls where [Challenge
    int memoryCounter = 0;
    int memoryCursor = 0; //to be used to track where user is in memory,
    public Challenge() {
        for(int i = 0; i < 20; i++) {
            rollMemory.add(i, null);
        }
    }

    public void dailyChallenge() {
        //example of a challenge type
        //selections from 3 unique categories.
        Selection choice1 = rollAndDisable();
        Selection choice2 = rollAndDisable();
        Selection choice3 = rollAndDisable();
        //roll added to memory
        addToRollMemory("Daily Challenge", choice1, choice2, choice3);
        //re-enable all disabled categories.
        reEnableCats(choice1,choice2,choice3); //re-enables all selected cats

        //now build text output for roll selections.
    }

    public void reEnableCats(Selection... selections) {
        for(Selection s : selections) {
            rolls.catEnable(rolls.masterCatMap.get(s.Category()));
        }
    }
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
    }
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

    }
    public void addToRollMemory(String challengeRoll, Selection... selections) {
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