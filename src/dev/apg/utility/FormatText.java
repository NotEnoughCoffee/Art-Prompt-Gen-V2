package dev.apg.utility;

import dev.apg.Selection;

import java.util.ArrayList;
import java.util.List;

final public class FormatText {
    //GENERIC TEXT FORMATTING TOOLS//
    @SuppressWarnings("unused")
    static String nL = "\n"; //new line
    @SuppressWarnings("unused")
    static String aOrAn(String s) {
        return switch(String.valueOf(s.charAt(0)).toUpperCase()) {
            case "A", "E", "I", "O", "U" -> "an " + s;
            default -> "a " + s;
        };
    } //appends 'a ' or 'an ' before a string depending on the first letter of the string

    //CATEGORY + SELECTION NAME FORMATTING//
    public static String titleCaps(String title) {
        //ensures text formatting for specific elements are unified by capitalizing the first letter of every word.
        String[] multiWord;
        StringBuilder formatted = new StringBuilder();
        String string;
        if(title.contains(" ")) {
            multiWord = title.split(" ");
            for (String s : multiWord) {
                formatted.append(capWord(s)).append(" ");
            }
            string = formatted.toString().trim();
        } else {
            string = capWord(title);
        }
        detectNumber(string);
        return string;
    }
    private static String capWord(String string) {
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1).toLowerCase();
    }
    private static void detectNumber(String string) {
        for (String s: string.split("")){
            try {
                Integer.parseInt(s);
                System.out.println("Warning: Number detected in string: " + string);
                break;
            }catch (Exception ignored) {}
        }
    }

    //OTHER TEXT FORMATTING//

    public static List<String> balanceMultiline (String string, int lineCount) {
        if(!string.contains(" ")) {
            System.out.println("No Spaces Detected for String: " + string + " || balanceMultiline skipped");
            return new ArrayList<>(List.of(string));
        }
        List<String> balancedLines = new ArrayList<>();
        String[] words = string.split(" ");
        int wordCount = words.length; //total number of words
        int counter = 0; // will keep track as words are added to each line
        int avgWordsPerLine = wordCount / lineCount;
        boolean isRemainder = false;
        int remainder = 0;
        if( wordCount % lineCount != 0 ) { //Extra word will be added for # (of remainder) lines
            remainder = wordCount % lineCount;
            isRemainder = true;
        }
        for(int i = 0; i < lineCount; i++) {
            StringBuilder currentLine = new StringBuilder();
            for(int k = 0; k < avgWordsPerLine; k++) { //adds average number of words to the current line
                currentLine.append(words[counter]).append(" ");
                counter++;
            }
            if(isRemainder && remainder > 0) { //extra word added if any left in the remainder.
                currentLine.append(words[counter]).append(" ");
                counter++;
                remainder--;
            }
            balancedLines.add(i, String.valueOf(currentLine)); //line added to list
        }
        return balancedLines;
    }
    public static String formatRollTextOutput(List<Selection> currentRollMemory) {
        StringBuilder string = new StringBuilder();
        for(Selection selection : currentRollMemory) {
            string.append(selection.Category()).append(": ");
            //string.append(aOrAn(selection.name())).append(";;");
            string.append(selection.name()).append(";;");
        }
        return String.valueOf(string);
    } //REMOVE ONCE NEW METHODS TAKE OVER
    public static StringBuilder formatRollMemorySave(String[] line) {
        StringBuilder saveLine = new StringBuilder();
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
        return saveLine;
    }
}