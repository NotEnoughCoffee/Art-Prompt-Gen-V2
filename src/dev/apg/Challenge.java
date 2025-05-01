package dev.apg;

import dev.apg.utility.FileLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Challenge extends FileLoader {
    public static String name = "Default Challenge";
    static Rolls rolls = new Rolls(); //creates Category map, grants access to roll methods
    final private static String memoryFileLocation = "/dataStorage/RollMemory.txt";
    static List<RollMemory> rollMemory = new ArrayList<>(20); //stores previous rolls
    private int previousMemoryCounter; //loads last memoryCounter location
    private int memoryCounter = 0; //tracks the location for memory to be stored //-1 offsets first stored memory
    private int memoryCursor = 0; //tracks user location while browsing previous rolls
    final private static String challengeListFileLocation = "/dataStorage/access/ChallengeList.txt";
    public static List<String[]> challengesList;
    public static int challengeIndex = 0;
    public  List<Selection> currentRollMemory;
    public boolean modifierActive = false;
    public Challenge() {
        clearRollMemory(); //initiate memory and loads list with null
        loadMemory();
        loadChallengeList();
    }
    private void loadMemory() {
        List<String[]> memoryLoad = loadFile(memoryFileLocation);
        if(memoryLoad.size() > 1) {
            try {
                previousMemoryCounter = Integer.parseInt(memoryLoad.get(memoryLoad.size() - 1)[0]);
                memoryLoad.remove(memoryLoad.size() - 1);
            }catch(Exception e) {
                System.out.println("Unable to Parse Int from RollMemory Save File, counter set to 0.\nUnexpected memory overwrite may occur.");
                previousMemoryCounter = 0;
            }
        }
        for(String[] line : memoryLoad) {
            List<Selection> selectionList = new ArrayList<>();
            for (int i = 1; i < line.length; i++) {
                selectionList.add(rolls.searchCatMap(line[i]));
            }
            addToRollMemory(line[0], selectionList);
        }
        memoryCounter = previousMemoryCounter;
        memoryCursor = previousMemoryCounter;
    } //tested and works as intended
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
                if (word.contains("null")) {
                    continue;
                }
                if (word.contains("[")) {
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
        saveData.append(memoryCounter);
        saveFile(memoryFileLocation, String.valueOf(saveData));
    } //tested and works as intended
    public void clearRollMemory() {
        rollMemory.clear();
        for(int i = 0; i < 20; i++) {
            rollMemory.add(i, null);
        }
    } //tested and works as intended
    private void loadChallengeList() {
        challengesList = loadFile(challengeListFileLocation);
        Challenge.name = challengesList.get(0)[0];
    }




    public void runChallenge() {
        String[] currentChallenge = challengesList.get(challengeIndex);
        try {
            runChallengeNEW(currentChallenge[0], Integer.parseInt(currentChallenge[1]), Boolean.parseBoolean(currentChallenge[2]));
        }catch (Exception e) {
            System.out.println("Error Reading Challenge From Challenge List: " + Arrays.toString(currentChallenge));
        }
    }
    //OLD CODE - TO BE REPLACED //
    public void runDefaultChallenge() {
        //Default challenge which picks 2 options, each from a unique enabled category.
        runChallenge("Default Test Challenge",3, false);
    } // for testing and initial setup
    public void runChallenge(String name, int selectionCount, boolean allowCategoryRepeats) {
        //runs the challenge with specified parameters and returns a list of the results
        Challenge.name = name; //name is assigned via overloaded method inserting currentChallenge[0] as the new name
        List<Selection> choices = new ArrayList<>();
        for(int i = 0; i < selectionCount; i++){
            if(allowCategoryRepeats) {
                choices.add(i, roll());
            }else{
                choices.add(i, rollAndDisable());
            }
        }
        addToRollMemory(name, choices); //roll added to memory
        setCurrentMemory();
        saveMemory();
        reEnableCats(choices); //re-enables all selected cats (regardless if they were disabled)
    } //tested and works
    //***** Needs to be reconfigured once GUI implemented
    //Output list not tested
    //END REPLACE//


    //RUN CHALLENGE REWRITE//

    //new Unit Test Challenge
    @SuppressWarnings("unused")
    public void challenge_Run_Challenge_NEW_Test() {
        runChallengeNEW("Test NEW", 4,false);
    }


    //Randomized Challenge - takes a count of number of selections to be chosen to randomly pick a challenge, with no category input
    public void runChallengeNEW(String name,  int selectionCount,boolean allowCategoryRepeats) {
        //New Base challenge roll
            //get and set challenge name + repeats boolean
            //a modifier boolean? -> needs entire method to roll
            //THIS: get a selection count (to run random selections) -> and build a category list off that and then roll those in the bellow method
                //AND: Overloaded method to get a Category List that rolls each of those selections
        List<Category> chosenCategories = new ArrayList<>();
        for(int i = 0; i < selectionCount; i++){
            if(allowCategoryRepeats) {
                chosenCategories.add(i,rolls.rollCategory());
            }else{
                chosenCategories.add(i,rolls.rollCategory());
                chosenCategories.get(i).enabled = false;
            }
        }
        runChallengeNEW(name, chosenCategories);
    }

    //Specified Challenge - takes a List of Categories to and rolls those specific category
    public void runChallengeNEW(String name, List<Category> chosenCategories) {
        Challenge.name = name;
        Selection mod = runModifiers();
        List<Selection> choices = new ArrayList<>();
        for(Category category : chosenCategories) {
            choices.add(rolls.rollSelection(category));
        }
        if(mod != null) {
            choices.add(mod);
        }
        addToRollMemory(name,choices);
        setCurrentMemory();
        saveMemory();
        reEnableCats(choices);
    }

    public Selection runModifiers() {
        //Checks if Modifiers are Active and if passes weighted roll, a modifier will be returned. otherwise null is returned.
        try {
            return modifierActive && rolls.rarityPassSuperRare() ? rolls.rollSelection(rolls.masterCatMap.get(rolls.mods)) : null;
        } catch (Exception e) {
            System.out.println("Error with runModifiers, function skipped");
            return null;
        }
    }







    public void reEnableCats(List<Selection> selections) {
        for(Selection selection : selections) {
            rolls.catEnable(rolls.getCategory(selection));
        }
    } //tested and works as intended
    //use two below methods with a navigation method to display previous or next rolls based on the memory location?
    public void setCurrentMemory() {
        if(memoryCursor >20) {
            memoryCounter = 0;
        }
        if(memoryCursor < 0) {
            memoryCursor = 19;
        }
        currentRollMemory = new ArrayList<>(rollMemory.get(memoryCursor).selections());
    }
    public void forwardMemory() {
        //moves memory location cursor forwards
        memoryCursor++;

        if(memoryCursor >= 20) {
            memoryCursor = 0;
        }
        if(rollMemory.get(memoryCursor) == null) {
            memoryCursor = 0;
        }//brings back to beginning if noting is in the highest available memory slot

        setCurrentMemory();
    } // navigation not implemented
    public void backwardMemory() {
        //moves memory location cursor backwards
        memoryCursor--;
        if(memoryCursor == -1) {
            for(int i = 19; i > 0; i--) {
                if (rollMemory.get(i) != null) {
                    break;
                }
            }
            memoryCursor = 19;
        }
        setCurrentMemory();

    } // navigation not implemented
    public void addToRollMemory(String challengeRoll, List<Selection> selections) {
        if(memoryCounter == 20) {
            memoryCounter = 0;
            //keeps last 20 rolls
        }
        memoryCursor = memoryCounter; //brings the cursor to the current entry that is being saved.
        rollMemory.set(memoryCounter,new RollMemory(challengeRoll,selections));
        memoryCounter++;
    } //tested and works as intended
    public Selection rollAndDisable () {
        //by default, categories remain enabled after a selection is made.
        Selection selection = roll();
        try {
            rolls.catDisable(selection);
        }catch (Exception e) {
            System.out.println("Error with disabling category for: " + selection + " in: " + selection.Category());
        }
        return selection;
    } //tested and works as intended
    public Selection roll() {
        //category remains enabled after selection is made.
        return rolls.rollSelection(rolls.rollCategory());
    } //tested and works as intended


}