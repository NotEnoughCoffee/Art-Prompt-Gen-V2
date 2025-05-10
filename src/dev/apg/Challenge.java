package dev.apg;

import dev.apg.gui.DisplayUI;
import dev.apg.utility.FileLoader;
import dev.apg.utility.FormatText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Challenge extends FileLoader {

    //BASIC SETUP//
    final private static String memoryFileLocation = "/dataStorage/RollMemory.txt";
    final private static String challengeListFileLocation = "/dataStorage/access/ChallengeList.txt";
    public static String name = "Default Challenge";
    public boolean modifierActive = false;
    public static Rolls rolls = new Rolls();

    //ROLL MEMORY + MEMORY NAVIGATION//
    static List<RollMemory> rollMemory = new ArrayList<>(20); //stores previous rolls
    private int previousMemoryCounter; //loads last memoryCounter location
    private int memoryCounter = 0; //tracks the location for memory to be stored
    private int memoryCursor = 0; //tracks user location while browsing previous rolls

    //CHALLENGE LIST + ACTIVE MEMORY TRACKING/
    public  List<Selection> currentRollMemory;
    public static List<String[]> challengesList;
    public static int challengeIndex = 0;

    //INITIALIZE//
    public Challenge() {
        clearRollMemory();
        loadMemory();
        loadChallengeList();
    }

    //FILE LOADING AND SAVING//
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
    }
    public void saveMemory() {
        //Saves Memory File
        StringBuilder saveData = new StringBuilder();

        for (RollMemory memory : rollMemory) {
            if (memory == null) {
                continue;
            }
            StringBuilder saveLine;
            String[] line = memory.toString().split(",");
            saveLine = FormatText.formatRollMemorySave(line);
            saveData.append(saveLine);
            saveData.setLength(saveData.length() - 1);
            saveData.append("\n");
        }
        saveData.append(memoryCounter);
        saveFile(memoryFileLocation, String.valueOf(saveData));
    }
    public void clearRollMemory() {
        rollMemory.clear();
        for(int i = 0; i < 20; i++) {
            rollMemory.add(i, null);
        }
    }
    private void loadChallengeList() {
        challengesList = loadFile(challengeListFileLocation);
        Challenge.name = challengesList.get(0)[0];
    }

    public void setPromptButton() {
        String[] currentChallenge = challengesList.get(challengeIndex);
        Challenge.name = currentChallenge[0];
    }
    //RUN CHALLENGE HANDLERS//

    //Runs Currently Selected Challenge
    public void runChallenge() {
        String[] currentChallenge = challengesList.get(challengeIndex);
        try {
            runChallenge(currentChallenge[0], Integer.parseInt(currentChallenge[1]), Boolean.parseBoolean(currentChallenge[2]));
        }catch (Exception e) {
            System.out.println("Error Reading Challenge From Challenge List: " + Arrays.toString(currentChallenge));
        }
    }

    //Randomized Challenge - takes a count of number of selections to be chosen to randomly pick a challenge, with no category input
    public void runChallenge(String name, int selectionCount, boolean allowCategoryRepeats) {
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
        runChallenge(name, chosenCategories);
    }

    //Specified Challenge - takes a List of Categories to and rolls those specific category
    public void runChallenge(String name, List<Category> chosenCategories) {
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

    //Modifier Active check and Selection
    public Selection runModifiers() {
        //Checks if Modifiers are Active + if it passes a weighted roll, a modifier will be returned. otherwise null is returned.
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
    }

    //ROLL MEMORY HANDLING//
    public void setCurrentMemory() {
        if(memoryCursor >20) {
            memoryCounter = 0;
        }
        if(memoryCursor < 0) {
            memoryCursor = 19;
        }
        currentRollMemory = new ArrayList<>(rollMemory.get(memoryCursor).selections());
        DisplayUI.setCurrentRollMemory(currentRollMemory);
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
    }
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

    }
    public void addToRollMemory(String challengeRoll, List<Selection> selections) {
        if(memoryCounter == 20) {
            memoryCounter = 0;
            //keeps last 20 rolls
        }
        memoryCursor = memoryCounter; //brings the cursor to the current entry that is being saved.
        rollMemory.set(memoryCounter,new RollMemory(challengeRoll,selections));
        memoryCounter++;
    }
}