package dev.tg;

import java.util.ArrayList;
import java.util.List;

public class Challenge extends FileLoader {
    static Rolls rolls = new Rolls(); //creates Category map, grants access to roll methods
    final private static String memoryFileLocation = "/dataStorage/RollMemory.txt";
    List<RollMemory> rollMemory = new ArrayList<>(20);
    //Stores previous rolls where [Challenge
    private int memoryCounter = -1; //tracks the location for memory to be stored //-1 offsets first stored memory
    private int memoryCursor = -1; //tracks user location while browsing previous rolls
    public Challenge() {
        clearRollMemory(); //initiate memory and loads list with null
        loadMemory();
    }

    private void loadMemory() {
        List<String[]> memoryLoad = loadFile(memoryFileLocation);
        for(String[] line : memoryLoad){
            List<Selection> selectionList = new ArrayList<>();
            for(int i = 1; i < line.length; i++){
                selectionList.add(rolls.searchCatMap(line[i]));
            }
            addToRollMemory(line[0],selectionList);
        }

    }
    public void saveMemory() {
        //each line needs to be saved as "Challenge Text" , Selection List separated by commas
        //and make sure if the element is null it is skipped
        StringBuilder saveData = new StringBuilder();

        for (RollMemory memory : rollMemory) {
            if (memory == null) {
                continue;
            }
            StringBuilder saveLine = new StringBuilder();
            String[] line = memory.toString().split(",");
            for(String word : line) {
                if (word.equalsIgnoreCase("null")) {
                    word = null;
                } else if (word.contains("[")) {
                    word = word.substring(1);
                    saveLine.append(word).append(",");
                } else if (word.contains("]")) {
                    word = word.substring(0, word.lastIndexOf("]"));
                    saveLine.append(word).append(",");
                } else {
                    word = word.trim();
                    saveLine.append(word).append(",");
                }
            }
            saveData.append(saveLine);
            saveData.setLength(saveData.length() - 1);
            saveData.append("\n");
        }
        saveData.setLength(saveData.length() - 1);
        saveFile(memoryFileLocation, String.valueOf(saveData));
    }
    public void clearRollMemory() {
        rollMemory.clear();
        for(int i = 0; i < 20; i++) {
            rollMemory.add(i, null);
        }
    }
    public List<Selection> runDefaultChallenge() {
        //Default challenge which picks 2 options, each from a unique enabled category.
        return runChallenge(2, false);
    } // for testing and initial setup

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
            rolls.catEnable(rolls.getCategory(s));
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
        rolls.catDisable(selection);
        return selection;
    } //untested
    public Selection roll() {
        //category remains enabled after selection is made.
        return rolls.rollSelection(rolls.rollCategory());
    }
}