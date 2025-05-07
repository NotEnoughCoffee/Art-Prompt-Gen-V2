package dev.apg.gui;

import dev.apg.Category;
import dev.apg.Challenge;
import dev.apg.Selection;
import dev.apg.utility.FileLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DisplayUI extends FileLoader {

    //BASIC SETUP//
    public static String promptButtonText;
    public String uiText;
    public int x, y;
    private final Rectangle uiDimensions = new Rectangle(80, 212, 640, 220);
    public static List<Selection> currentRollMemory;

    //GUI SETUP//
    GUI gui;
    Graphics2D g2D;
    Font ariel_32, ariel_28, ariel_72;
    BufferedImage background;
    final String backgroundImageFileLocation = "/graphics/90s_Background_V2.png";

    //INITIALIZATION//
    public DisplayUI(GUI gui) {
        this.gui = gui;
        this.background = loadImage(backgroundImageFileLocation);
        ariel_32 = new Font("Ariel", Font.BOLD, 32);
        ariel_28 = new Font("Ariel", Font.BOLD, 28);
        ariel_72 = new Font("Ariel", Font.BOLD, 72);
    }

    //DRAW METHODS//
    public void draw(Graphics2D g2d) {
        //Ran when repaint -> paintComponent is called on GUI class
        //This method redraws the screen, reloads the 'background' image and loaded text elements
        this.g2D = g2d;
        g2d.drawImage(background, 0, 0, null);

        //MARKED FOR UPDATE//
        setFont(g2d, ariel_32);
        g2d.setColor(Color.black);
        //                 //

        drawText();
    }

    public void drawText() {
        resetUIText();
        //setUIText(gui.rollText);
        g2D.setColor(Color.black);
        drawPromptButtonText();
        setResultsDisplay(currentRollMemory);

        //OTHER NOTES://
        //alter challenge roll methods to min 1, max 5 categories (if it's a repeat don't add to the List) + the method that takes List<Category> will roll each category in the list 1 time, then if there's a difference in requested roll count and rolls already made, then it randomly rolls categories from the list (or a different handler method to deal with EXACT rolls to be made if specified in the saved ChallengeList details) (AND Maybe a limit on overall rolls per category)

        //make a music mute button that moves away from cursor when in a range
        //            //

        //clean up above
        //To draw the text where categories are displayed at the top and the selection below:


        // First I need to build a List<Category> of all the rolled challenges in the List<Selection>
        // I will use that to find any duplicates, and log the duplicates somehow
        // this will allow me to combine multiple results of the same category when displayed

        //then I need a sub list of unique category names and a count
        // the count will be used to determine how the screen is split


        /// screenSplit could be a method that returns a rectangle[] based on the category count that uses math to determine width and a loop.
        // a loop of for(int i = 0; i < rect.size? ; i++) ->
        // Display a randomized polygon graphics based on 2 subsections of rectangle[i]. make a polygon that picks points roughly in subsections of the subsection rectangle, and makes a 4 sided shape from that (to be weird and funky?)
        // -> later, this is lower priority and its own method
        // Display List<Cat>[i].name; centered in a defined subsection of rectangle[i]; (using dimensions of text to determine if text size needs to be shrunk or wrapped to fit in space (detect " ") ) (own method)
        // THEN choose each selection in List<selection> that has matching .category() and display each on their own line, in the lower subsection of the rectangle[i] using width detection of text (so make its own method) to determine font size and word wrap if spaces. (or pick the widest word to determine width?) using its own method that takes the matching selections as a (Selection... selections) parameter and getting its  count to determine the below splitting:
        // if multiple selections, split into string[]; and use a loop to divide the subsection for each result (and changing display graphic to show a line between choices)
        // may have to divide differently if 4+ selections in a 1-2 category challenge or just limit to 4 per category max


        //REMOVE//
        //Below is a temporary solution to display roll text until challenge class changes are implemented
        String[] text = uiText.split(";;");
        y = 150 + (350 / 2) - ((text.length - 1) * 39) / 2;
        for (String s : text) {
            getFontCenterX(s);
            g2D.drawString(s, x, y); //use this to draw multiple strings with a loop
            y += 40;
        }
        //REMOVE//
    }

    //FOR REMOVAL//
    public void drawPromptButtonText() {

        setFont(g2D, ariel_72);
        getFontCenterX(Challenge.name);
        y = 140; //35 + (105/2) +72/4;
        g2D.drawString(Challenge.name, x, y);

        setFont(g2D, ariel_32);
    }

    public void setResultsDisplay(List<Selection> selections) {
        g2D.setColor(Color.white);
        List<Category> categories = getUniqueCategories(selections);
        int count = categories.size();
        Rectangle[] displayAreas = splitScreen(count);

        for (int i = 0; i < count; i++) {
            //Draw display area polygon -> implement later
            String catName = categories.get(i).name;
            Font catFont = setFontSize(catName, displayAreas[i].width, displayAreas[i].height);
            //Draw Category centered and sized text using rectangle dimensions.
            //change rectangle y = y + yHeight; yHeight = uiDimensions - yHeight;
            // that should return the dimensions of the text display area for selection
            // -> then split that into a sub rect array based on number of selections in the current category

            //now draw selection text centered and sized using the new dimensions via another loop AND DONE
            //
        }

    }


    private String getWidestLine(String[] stringArray, List<String> stringList) {
        String widestLine;
        if(stringArray != null) {
            widestLine = stringArray[0];
            for(String string : stringArray) {
                if (string.length() > widestLine.length()) {
                    widestLine = string;
                }
            }
            return widestLine;
        }else if(stringList != null) {
            widestLine = stringList.get(0);
            for(String string : stringList) {
                if (string.length() > widestLine.length()) {
                    widestLine = string;
                }
            }
            return widestLine;
        }
        else return null;
    }

    public List<String> balanceLine(List<String> text, int wordCount, int lineSplits) {
        String reformedLine = String.join(" ", text); //turns the List back into a line
        String[] words = reformedLine.split(" "); //collects each word
        List<String> balancedText = new ArrayList<>();
        int[] updatedWordCount = new int[lineSplits]; //will keep track of words to be held per line


        int[][] wordsLetterCount = new int[words.length][];
        wordsLetterCount[0] = new int[wordCount];
        //2D array that holds the count of each words length per line
        //[0] initially holds all the words
        //[x] holds no words; loop will overwrite the array and split out the last int

        for (int i : wordsLetterCount[0]) {
            wordsLetterCount[0][i] = words[i].length();
        } //nabs the lengths of the characters in each word and adds them to the first line of the array
        int[] spacesCount = new int[lineSplits]; //will store how many spaces based on word count per line - 1;

        for(int i = 0; i < lineSplits; i++) {


        }




        return balancedText;
    }







    //this could be changed to void drawTextInDisplayArea (String, Rectangle)
    private Font setFontSize(String displayText, int displayWidth, int displayHeight) { //displayText needs to be fed in as "Words with Spaces between them"
        Font broadwayBaseLine50 = new Font("Broadway", Font.PLAIN, 50); //For Testing

        String[] words; // contains all the words in the text to be displayed. will be broken up into multiple lines?
            if (displayText.contains(" ")) {
                words = displayText.split(" ");
            } else {
                words = new String[]{displayText};
            }
        String widestWord = getWidestLine(words, null);

        int pixelWidth; //used to check the pixel with of the selected String at specified font size
        int fontSize = 50; //largest font size to test
        int multiLineFontSize = 38; //largest font size for multiLine to test

        int lineCount = 1; //keeps track of how many lines to split
        int[] wordCount = new int[]{words.length}; //keeps track of how many words are each line when splitting occurs

        Font currentFont = null; //sets the font to be used for displaying text by dynamically changing size

        List<String> lineToBeSplit = new ArrayList<>(List.of(displayText)); //holds the lines being split and balanced
        //starts with displayText because balanceLine method will take all the strings and balance it across

        List<String> balancedLine = new ArrayList<>(List.of(displayText)); //to hold the dynamically split and balanced array of lines
        //starts with displayText because it wont need to be updated if it stays a single line


        boolean[] textPass = {false, false}; // { [0] WHILE LOOP PASS FLAG // [1] MULTILINE SPLIT FLAG }

        // REMOVE:
        //StringBuilder currentLineText = new StringBuilder();
        //int multiLinePixelHeight = ((multiLineFontSize * 2) / 3 * 4) + 2;

        while (!textPass[0]) {
            currentFont = new Font("Broadway", Font.PLAIN, (textPass[1] ? multiLineFontSize : fontSize)); //sets the current font size using updated size from previous loop

            if(!textPass[1]) {
                //Runs text through dynamic sizing until text is 8pt OR a check at 32pt detects multiple words and splits it
                pixelWidth = getPixelWidthOfWord(displayText, currentFont);
                textPass = textWidthCheck(fontSize, pixelWidth, displayWidth, displayHeight, displayText, currentFont);
                //resets boolean after checking if the specified text (displayText) fits inside the specified dimensions
                if (!textPass[1]) {
                    //if not multiline is still false, keep decreasing font size until 8.
                    //either 8 or pass will exit out of this while loop with a 'single' word line
                    fontSize--;
                    continue;
                }
            } //if MultiLine Flag is not triggered, text will be tested to fit on one line.
            //if above passes, While loop will be exited with text fitting on one line in the specified space, and a font size saved for that.

            //MULTILINE TRIGGERED// -> multiple words detected in the displayText and lower bound of 32pt reached. Text will now be split to multiple lines:

                if (words.length == 2) { //Checks word count if there are only two words
                    pixelWidth = getPixelWidthOfWord(widestWord, currentFont);
                    lineCount = 2;
                    textPass = textWidthCheck(multiLineFontSize, pixelWidth, displayWidth, displayHeight, widestWord, currentFont);
                    if (!textPass[0]) {
                        multiLineFontSize--;
                    }
                } else if (words.length >= 3) {
                    if(lineCount < 2) { //makes sure there's is at least two lines
                        lineCount = 2;
                    }

                    //NEW:
                    //lineCount = 2 + loopCounter; (no increment this later in statement
                    //lineToBeSplit.add(0, displayText); I dont think this matters now as the list is loaded with the displaytext to start
                    //balancedLine = balanceLine(line, wordCount, lineCount);

                    //get widest line in balanced line
                    //pixelWidth = getPixelWidthOfWord(widestLine, currentFont);

                    //textPass = textWidthCheck(multiLineFontSize, pixelWidth. displayWidth, displayHeight, widestLine, currentFont);
                    if(!textPass[0]) {
                        multiLineFontSize--;
                    }





                   //loopcounter++;
                }
        }


        //once out of loop
        //currentFont will be the proper text size
        //line count will keep track of how many text lines there are.
        //currentLine will keep track of the first line of the text box.

        //function to get longest segment out of a string[]. loops through strings and combines them ( word count - 1 ) and -- per loop.
        // int splitcount = 2; which is also line count
        // SAMPLE  "THIS IS A SIX WORD STRING"
        //loop 1 -> "THIS IS A SIX WORD" and "STRING" 17 / 6                        //// first string shorter than the last string
        //loop 2 -> "THIS IS A SIX" and "WORD STRING" 13 / 11
        //loop 3 -> "THIS IS A" and "SIX WORD STRING" 9 / 15 -> trigger.
        // until the second string is longer than the first. by character length.
        //then it will spit out a String[] that compares the last two loops and  shorter of loop2 [0] or loop3 [1] wins.
        //textWidth Check is Ran on the longer string but only until multiline font size is 75% of its starting (38)/4*3 = 28
        //if " " space count in each String in [] is <= 1 (AND fontsize > 27) ; keep shrinking font size (until 28)
        //otherwise split another line can be split.
        //set font size back to 38?
        //new loop and splitcount +1
        //loop 1 -> "THIS IS A SIX" and "WORD" and "STRING" 13 / 4 / 6 (loop looks at last 2 in String[]; RECURSION?S??S
        //loop 2 -> "THIS IS A" and "SIX WORD" and "STRING" 9 / 8 / 6
        // (if last 2 in [] do not have " "; then move a word from string[0] to [1]
        //loop 3 -> "THIS IS A" and "SIX" and "WORD STRING" 9 / 3 / 11 -> trigger
        // (if there is a " ", then the second word is split from [1] to [2]
        //loop 4 -> "THIS IS A" and "SIX WORD" and "STRING" 9 / 8 / 6
                // -> length is compared like above; shorter of loop2 [1] or loop3 [2] wins. and then loop continues with first two
        //loop 5 -> "THIS IS" and "A SIX WORD" and "STRING" 7 / (10 / 6) -> either trigger?
                // this winner is then compared to the first string. so loop 4 is the output to check if it wins
        //textwidth is checked again, until font is another 75% of new current so (28)/4*3 = 21
        //again if space count is <= 1; keep shrinking until 21;
        //if the words still dont fit after 3 lines at 21 font size
        //new loop splitcount +1 (4 currently)
        //loop1 -> "THIS IS A" and "SIX" and "WORD" and "STRING" 9 / 3 / 4 / 6
        //loop2 -> "THIS IS" and "A SIX" and "WORD" and "STRING" 7 / 5 / 4 / 6  ( word is moved through until last one gains a word)
        //loop3 -> "THIS IS" and "A" and "SIX WORD" and "STRING"
        //loop4 -> THIS IS / A / SIX / WORD STRING
        //

        //but what if
        // loop 1 -> IT IS A / SIX / WORD / STRING  - 7 / 3 / 4 / 6
        // loop 2  -> "IT IS" and "A SIX" and "WORD" and "STRING" 5 / 5 / 4 / 6 (should be ideal?) -> trigger

        // loop 3 -> 7 / 3 / 4 / (6) won loop1[3] vs loop2[4]
        // loop 4 -> IT IS / A SIX / WORD / STRING - 5 / 5 / 4 / (6)
        // loop 5 -> loop3  (1) wins again due to 3 vs 4. -> 7 / 3 / (4 / 6)
        // loop 6 -> IT IS / A SIX / WORD / STRING - 5 / 5 / ( 4 / 6 )
        // loop 7 -> IT / IS A SIX / WORD / STRING - 2 / 8 / (4/6) -> trigger
        // loop 6 wins and then this is tested for width



        /// math

        // 2_2_2_2_2 / 2 / 2 / 2 -> 14 / 2 / 2 / 2 (8 words 2 letter each)
        // 2 2 2 2 / 2 2 / 2 / 2 -> 11 / 5 / 2 / 2
        // 2 2 2 2 / 2 / 2 2 / 2 -> 11 / 2 / 5 / 2
        // 2 2 2 2 / 2 / 2 / 2 2 -> 11 / 2 / 2 / 5 no " " in [2] to feed into [3]
        // 2 2 2 / 2 2 / 2 / 2 2 -> 8 / 5 / 2 / 5
        // 2 2 2 / 2 / 2 2 / 2 2 -> 8 / 2 / 5 / 5
        // 2 2 2 / 2 / 2 / 2 2 2 -> 8 / 2 / 2 / 8 WIN
        // 2 2 / 2 2 / 2 / 2 2 2 -> 5 / 2 / 2 / 8 -> trigger
        // 2 2 2 / 2 / 2 / 2 2 2 -> 8 / 2 / 2 / (8)
        // 5 5 2
        // 5 2 5 WIN -> WIN (shorter or first iteration in case of tie) so 5 2 5 8 is bad line output
        // 2 5 5
        // needs extra rules for word count vs split count

        //now reverse it to balancE?
        // 2 2 2 / 2 2 / 2 / 2 2 ->  8 5 2 5
        // 2 2 2 / 2 / 2 2 / 2 2 -> 8 2 5 5 wins
        // 2 2 2 / 2 / 2 / 2 2 2 -> 8 2 2 8
        // 2 2 2 / 2 / 2 2 / 2 2 -> 8 2 5 (5)
        // 2 2 / 2 2 / 2 2 / 2 2 -> 5 5 5  win
        // 5 2 8 -> trigger
        //


        //"Dude you
        // would a
        // try an
        // oboe car"
        // 4_3_5_1_3 / 2 / 4 / 3 -> 20.2.4.3
        // 4 3 5 1 / 3 2 / 4 / 3 -> 16.6.4.3
        // 4 3 5 1 / 3 / 2 4 / 3 -> 16.3.7.3
        // 4 3 5 1 / 3 / 2 / 4 3 -> 16.3.2.8 > repeat
        // 4 3 5 / 1 3 / 2 / 4 3 -> 14.5.2.8
        // 4 3 5 / 1 / 3 2 / 4 3 -> 14.1.6.8
        // 4 3 5 / 1 / 3 / 2 4 3 -> 14.1.3.11 WIN
        // 4 3 / 5 1 / 3 / 2 4 3 -> 8.7.3.11 -> trigger
        // 4 3 5 / 1 / 2 // -> 14.1.2
        // 4 3 / 5 1 / 2 // -> 8.7.2
        // 4 3 / 5 / 1 2 // -> 8.5.4 > repeat
        // 4 / 3 5 / 1 2 // -> 4.9.4 -> trigger + WIN
        // 4 / 3 5 // -> 4.9 -> loop skipped

        //Balance currently at: // 4 / 3 5 / 1 2 / 3 4 3 = 4.9.4.12
        //REVERSE             : // 3 4 3 / 2 1 / 5 3 / 4 = 12.4.9.4


        // 3 4 3 / 2 1 / 5 3 / 4 -> 12.4.9.4
        // 3 4 / 3 2 1 / 5 3 / 4 -> 8.8.9.4
        // 3 4 / 3 2 / 1 5 3 / 4 -> 8.6.11.4
        // 3 4 / 3 2 / 1 5 / 3 4 -> 8.6.7.8 > repeat WIN
        // 3 / 4 3 2 / 1 5 / 3 4 -> 3.11.7.8 -> trigger

        // 3 4 / 3 2 / 1 5 // 8.6.7 same trigger and this will win again
        // and again
        // reversed : 4 3 // 5 1 // 2 3 // 4 3 yeah that works!





        // so what if.
        // gets word count in the string[] and divides split count into that.
        // split count of 2 lines with 8 words = start with 4 words per line
        // 2 2 2 2 / 2 2 2 2 WIN
        // 2 2 2 / 2 2 2 2 2
        // 5 2 2 2 / 2 2 2 2
        // 5 2 2 / 2 2 2 2 2 even win
        // 5 2 / 2 2 2 2 2 2 trigger

        //loop4 -> "THIS IS" and "A" and "SIX" and "WORD STRING"


        //do a modulus method call to itself in the return statement if a flag is true or some shit
        return currentFont;
    }


    private boolean[] textWidthCheck(int fontSize, int pixelWidth, int displayWidth, int displayHeight, String displayText, Font currentFont) {
        boolean[] textPass = {false, false};
        if (fontSize > 32 &&
                (pixelWidth > (displayWidth - 4) ||
                        getPixelHeightOfWord(displayText, currentFont) > (displayHeight - 4))) {
            //Font size too wide, fail and try again
            return textPass;
        } else if (pixelWidth <= displayWidth - 4) {
            //text size fits within the display and starts out at least being >= 32 font size
            textPass[0] = true;
            return textPass;
        } else if (!displayText.contains(" ") && fontSize >= 8) {
            //multiline checked at font size 32 if it can be split, function will return multiline boolean true
            //keeps reducing font size if Category name does not contain spaces
            if (fontSize == 8) {
                //if font size still not thin enough, the word is too long and text size will be set to 8
                System.out.println("Issue with Category Name Length, Word too long or no spaces detected, overlapping may occur");
                textPass[0] = true;
                return textPass;
            }
            return textPass;
        }else {
            textPass[1] = true;
            return textPass;
        }
    }


    public static void setCurrentRollMemory(List<Selection> selections) {
        currentRollMemory = selections;
    }

    private Rectangle[] splitScreen(int count) {
        //Returns an array of the display subsection dimensions for the category
        int xPoint = uiDimensions.x;
        int yPoint = uiDimensions.y;
        int displayWidth = uiDimensions.width / ((count > 0) ? count : 1);
        int displayHeight = uiDimensions.height * 36/100;
        Rectangle[] displayAreas = new Rectangle[count];
            for (int i = 0; i < count; i++){
                displayAreas[i] = new Rectangle(xPoint + (i * displayWidth), yPoint, displayWidth, displayHeight);
            }
        return displayAreas;
    }


    private List<Category> getUniqueCategories (List<Selection> selections) {
        List<Category> results = new ArrayList<>();
        if(selections != null){
            for (Selection selection : selections) {
                Category category = Challenge.rolls.getCategory(selection);
                if (!results.contains(category)) {
                    results.add(category);
                }
            }
        }
        return results;
    }

    //FORMATTING//
    public void resetUIText() {
        uiText = "";
        x = 0;
        y = 0;
    }
    public void setFont(Graphics2D g2d, Font font) {
        g2d.setFont(font);
    }
    public void setUIText (String string) {
        this.uiText = string;
    }
    public void getFontCenterX(String text) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        x = GUI.screenWidth / 2 - length / 2;
    } //sets X coordinate so text is centered on screen based on the strings width

    public int getPixelWidthOfWord(String word, Font font) {
        return (int) g2D.getFontMetrics(font).getStringBounds(word, g2D).getWidth();
    }

    public int getPixelHeightOfWord(String word, Font font) {
        return (int) g2D.getFontMetrics(font).getStringBounds(word,g2D).getHeight();
    }

        //UNIMPLEMENTED METHODS//

    //public void wordWrap (String[] line) {} -> potentially make on FormatText class


}
