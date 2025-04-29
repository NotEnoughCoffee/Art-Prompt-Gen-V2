package dev.tg;

import java.util.List;

public record RollMemory(String challengeRolled, List<Selection> selections) {
    private String selectionToString(List<Selection> selections) {
        StringBuilder selectionList = new StringBuilder();
        for(Selection selection : selections) {
            selectionList.append(selection).append(",");
        }
        selectionList.setLength(selectionList.length() -1);
        return String.valueOf(selectionList);
    }
    @Override
    public String toString() {
        return challengeRolled + "," + selectionToString(selections);
    }
}