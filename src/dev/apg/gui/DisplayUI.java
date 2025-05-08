package dev.apg.gui;

import dev.apg.Category;
import dev.apg.Challenge;
import dev.apg.Selection;
import dev.apg.utility.FileLoader;
import dev.apg.utility.FormatText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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
            drawTextInDisplayArea(catName, displayAreas[i]);
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

    private void drawTextInDisplayArea(String displayText, Rectangle displayArea) {
        String[] words; // contains all the words in the text to be displayed.
            if (displayText.contains(" ")) {
                words = displayText.split(" ");
            } else {
                words = new String[]{displayText};
            }
        String widestWord;

        int displayHeight = displayArea.height;
        int displayWidth = displayArea.width;

        int pixelHeight, pixelWidth;

        int fontSize = 50; //largest font size to test
        int multiLineFontSize = 50; //largest font size for multiLine to test

        int lineCount = 1; //keeps track of how many lines to split

        Font currentFont = null; //sets the font to be used for displaying text by dynamically changing size

        List<String> balancedLine = new ArrayList<>(List.of(displayText)); //to hold the dynamically split and balanced array of lines
        //starts with displayText because it won't need to be updated if it stays a single line


        boolean[] textPass = {false, false}; // { [0] WHILE LOOP PASS FLAG // [1] MULTILINE SPLIT FLAG }

        // REMOVE:
        //StringBuilder currentLineText = new StringBuilder();
        //int multiLinePixelHeight = ((multiLineFontSize * 2) / 3 * 4) + 2;

        while (!textPass[0]) {
            currentFont = new Font("Broadway", Font.PLAIN, (textPass[1] ? multiLineFontSize : fontSize)); //sets the current font size using updated size from previous loop

            if(!textPass[1]) {
                //Runs text through dynamic sizing until text is 8pt OR a check at 32pt detects multiple words and splits it
                pixelHeight = getPixelHeightOfWord(displayText,currentFont);
                pixelWidth = getPixelWidthOfWord(displayText, currentFont);
                textPass = textWidthCheck(pixelHeight, pixelWidth, displayWidth, displayHeight, displayText, currentFont);
                //resets boolean after checking if the specified text (displayText) fits inside the specified dimensions
                if (!textPass[0] && !textPass[1]) {
                    //if it did not pass and multiline is still false, keep decreasing font size until 8.
                    //either 8 or pass will exit out of this while loop with a 'single' word line
                    // 8 will pass because of conditions in textWidthCheck
                    fontSize--;
                    continue;
                }else if(textPass[1]) {
                    continue; //reset loop to reset font
                }
            } //if MultiLine Flag is not triggered, text will be tested to fit on one line.
            //if above passes, While loop will be exited with text fitting on one line in the specified space, and a font size saved for that.

            //MULTILINE TRIGGERED// -> multiple words detected in the displayText and lower bound of 32pt reached. Text will now be split to multiple lines:

                if (words.length == 2 && textPass[1]) { //Checks word count if there are only two words,
                    balancedLine = Arrays.asList(words);
                    widestWord = getWidestLine(null, balancedLine);
                    pixelHeight = getPixelHeightOfWord(widestWord, currentFont);
                    pixelWidth = getPixelWidthOfWord(widestWord, currentFont);
                    lineCount = 2;
                    textPass = textWidthCheck(pixelHeight, pixelWidth, displayWidth, displayHeight, widestWord, currentFont);
                    if (!textPass[0]) {
                        multiLineFontSize--;
                    }
                } else if (words.length >= 3 && textPass[1]) {
                    if(lineCount < 2) { //makes sure there's at least two lines
                        lineCount = 2;
                    }
                    balancedLine = FormatText.balanceMultiline(displayText,lineCount); //updates the balanced line with the displayText split evenly across lineCount # of lines.
                    widestWord = getWidestLine(null,balancedLine);
                    pixelWidth = getPixelWidthOfWord(widestWord,currentFont);
                    pixelHeight = (getPixelHeightOfWord(widestWord,currentFont) + 2) * lineCount; //height of lines + 2px spacing
                    textPass = textWidthCheck(pixelHeight, pixelWidth, displayWidth, displayHeight, widestWord, currentFont);
                    //if the ext passes here, the rest of the conditions below will be skipped.
                    if(!textPass[0]) { //if text does not pass, size is decreased.
                        multiLineFontSize--;
                        if(words.length != lineCount) { // if not already at max lines per words
                            //split to more lines
                            if (multiLineFontSize == 32 && lineCount == 2) {
                                multiLineFontSize = 42;
                                lineCount++;
                            }else if(multiLineFontSize == 28 && lineCount == 3) {
                                multiLineFontSize = 38;
                                lineCount++;
                            }else if(multiLineFontSize == 24 && lineCount >= 4) {
                                //with 4+ lines, increment lines every time text gets down to 24
                                multiLineFontSize = 36;
                                lineCount++;
                            }
                        }
                    }
                }
        }
        //once out of loop
        //currentFont will be the proper text size
        //lineCount will keep track of current number of lines.
        //balancedLine will contain all lines of text to be displayed, on however many lines it takes to fit box at currentFont size.

        //Text can now be output to be displayed
        drawTextInDisplayArea(balancedLine,currentFont,displayArea);

    }

    private void drawTextInDisplayArea (List<String> displayText, Font currentFont, Rectangle displayArea) {
        setFont(g2D, currentFont);


    }


    private boolean[] textWidthCheck(int pixelHeight, int pixelWidth, int displayWidth, int displayHeight, String displayText, Font currentFont) {
        boolean[] textPass = {false, false};
        int fontSize = currentFont.getSize();
        if (fontSize > 32 &&
                (pixelWidth > (displayWidth - 4) ||
                        pixelHeight > (displayHeight - 4))) {
            //Font size too wide or tall, fail and try again
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
