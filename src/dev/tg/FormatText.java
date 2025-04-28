package dev.tg;
final public class FormatText {

    static String nL = "\n"; //new line


    //unsure of other methods needed, most likely something for formatting the results output text.

    static String aOrAn(String s) {
        return switch(String.valueOf(s.charAt(0)).toUpperCase()) {
            case "A", "E", "I", "O", "U" -> "an " + s;
            default -> "a " + s;
        };
    }
    static String titleCaps(String title) {
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
    } //tested + works
    private static String capWord(String string) {
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1).toLowerCase();
    } //tested + works
    private static void detectNumber(String string) {
        for (String s: string.split("")){
            try {
                Integer.parseInt(s);
                System.out.println("Warning: Number detected in string: " + string);
                break;
            }catch (Exception ignored) {}
        }
    } //tested + works
}