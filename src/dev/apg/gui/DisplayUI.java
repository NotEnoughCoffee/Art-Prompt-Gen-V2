package dev.apg.gui;

import dev.apg.Category;
import dev.apg.Challenge;
import dev.apg.Selection;
import dev.apg.utility.FileLoader;
import dev.apg.utility.FormatText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DisplayUI extends FileLoader {

    //BASIC SETUP//
    public static String promptButtonText;
    public String uiText;
    public int x, y;
    private final Rectangle resultsDisplayDimensions = new Rectangle(80, 212, 640, 220);
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



        //REMOVE//
        //Below is a temporary solution to display roll text until challenge class changes are implemented
//        String[] text = uiText.split(";;");
//        y = 150 + (350 / 2) - ((text.length - 1) * 39) / 2;
//        for (String s : text) {
//            getFontCenterX(s);
//            g2D.drawString(s, x, y); //use this to draw multiple strings with a loop
//            y += 40;
//        }
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
        g2D.setColor(Color.white); //sets color to white for all 'Results' and 'Category Names'
        List<Category> categories = getUniqueCategories(selections);
        int categoryCount = categories.size();
        Rectangle[] displayAreas = splitScreen(categoryCount);


        for (int i = 0; i < categoryCount; i++) {
            //drawPolygon -> method to implement later to draw polygon based off of Rectangle Dimensions
            String catName = categories.get(i).name;
            drawTextInDisplayArea(catName, displayAreas[i]); //draws the text fed into this method split and centered in specified area

            List<String> resultsInCategory = new ArrayList<>();
            selections.forEach( s -> {
                if(s.Category().equalsIgnoreCase(catName)){
                    resultsInCategory.add(s.name());
                }
            });
            int resultCount = resultsInCategory.size();
            int subDisplayCounter = i + categoryCount;
            //second half of displayArea is now the results display area (so displayAreas[i + categoryCount])
            //create a sub space and loop through displaying each result

            Rectangle subDisplay = new Rectangle(
                    displayAreas[subDisplayCounter].x,
                    displayAreas[subDisplayCounter].y,
                    displayAreas[subDisplayCounter].width,
                    displayAreas[subDisplayCounter].height/resultCount);

            for(String line : resultsInCategory) {
                drawTextInDisplayArea(line,subDisplay);
                subDisplay.y += subDisplay.height;
            }
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
        int multiLineFontSize = 38; //largest font size for multiLine to test

        int lineCount = 1; //keeps track of how many lines to split

        Font currentFont = null; //sets the font to be used for displaying text by dynamically changing size

        List<String> balancedLine = new ArrayList<>(List.of(displayText)); //to hold the dynamically split and balanced array of lines
        //starts with displayText because it won't need to be updated if it stays a single line


        boolean[] textPass = {false, false}; // { [0] WHILE LOOP PASS FLAG // [1] MULTILINE SPLIT FLAG }

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

//                if (words.length == 2 && textPass[1]) { //Checks word count if there are only two words,
//                    balancedLine = Arrays.asList(words);
//                    widestWord = getWidestLine(null, balancedLine);
//                    pixelHeight = getPixelHeightOfWord(widestWord, currentFont);
//                    pixelWidth = getPixelWidthOfWord(widestWord, currentFont);
//                    lineCount = 2;
//                    textPass = textWidthCheck(pixelHeight, pixelWidth, displayWidth, displayHeight, widestWord, currentFont);
//                    if (!textPass[0]) {
//                        multiLineFontSize--;
//                    }
//                } else
                    if (words.length >= 2 && textPass[1]) {
                    if(lineCount < 2) { //makes sure there's at least two lines
                        lineCount = 2;
                    }
                    balancedLine = FormatText.balanceMultiline(displayText,lineCount); //updates the balanced line with the displayText split evenly across lineCount # of lines.
                    widestWord = getWidestLine(null,balancedLine);
                    pixelWidth = getPixelWidthOfWord(widestWord,currentFont);
                    pixelHeight = getPixelHeightOfWord(widestWord,currentFont) * lineCount; //height of all lines
                    textPass = textWidthCheck(pixelHeight, pixelWidth, displayWidth, displayHeight, widestWord, currentFont);
                    if(!textPass[0]) { //if text does not pass, size is decreased.
                        multiLineFontSize--;
                        if(words.length != lineCount) { // if not already at max lines per words -> split more lines
                            if (multiLineFontSize == 31 && lineCount == 2) {
                                multiLineFontSize = 42;
                                lineCount++;
                            }else if(multiLineFontSize == 27 && lineCount == 3) {
                                multiLineFontSize = 38;
                                lineCount++;
                            }else if(multiLineFontSize == 23 && lineCount >= 4) { //with 4+ lines, increment lines every time text gets down to 24
                                multiLineFontSize = 36;
                                lineCount++;
                            }
                        }
                    }
                }
        }
        drawTextInDisplayArea(balancedLine,currentFont,displayArea); //Text drawn
    }

    private void drawTextInDisplayArea (List<String> displayText, Font currentFont, Rectangle displayArea) {
        setFont(g2D, currentFont);
        //takes a List and Font and draws each line as a text box centered in the displayArea's bounds based.
        int centeredX;
        int centeredY = (displayArea.height/2 + displayArea.y) + ((displayText.size() * getPixelHeightOfWord("EXAMPLE", currentFont)) / 4) - (getPixelHeightOfWord("EXAMPLE",currentFont) * (displayText.size() - 1))/4*3;

        for(String text : displayText) {
            centeredX = (displayArea.x + displayArea.width/2) - getPixelWidthOfWord(text,currentFont)/2;
            g2D.drawString(text,centeredX,centeredY);

            g2D.draw(displayArea);
            centeredY += getPixelHeightOfWord(text,currentFont);
        }
    }


    private boolean[] textWidthCheck(int pixelHeight, int pixelWidth, int displayWidth, int displayHeight, String displayText, Font currentFont) {
        //checks if displayText fits in displayArea based on currentFont -> which is incremented each time this method is run inside a loop
        boolean[] textPass = {false, false};
        int fontSize = currentFont.getSize();
        int innerHeight = 15;
        int innerWidth = 20;
        if (fontSize > 20 &&
                (pixelWidth > (displayWidth - innerWidth) ||
                        pixelHeight > (displayHeight - innerHeight))) {
            //Font size too wide or tall, fail and try again
            return textPass; // text fails
        } else if (pixelWidth <= displayWidth - innerWidth && pixelHeight <= displayHeight - innerHeight) {
            //if the text fits in the display area, pass
            textPass[0] = true;
            return textPass; // pass true
        } else if (!displayText.contains(" ") && fontSize >= 8) {
            //WidthCheck will keep failing while fontSize is > 8. 8 is the minimum display size, and will be forced despite breaking box bounds
            if (fontSize == 8) {
                System.out.println("Issue with Category Name Length, word too long or no spaces detected, overlapping may occur");
                textPass[0] = true; // pass is forced
                return textPass;
            }
            return textPass; // pass fails
        }else {
            textPass[1] = true;
            if (fontSize == 8){ //minimum font size reached
                textPass[0] = true;
            }
            return textPass; // multiline passes because line contains " "
        }
    }


    public static void setCurrentRollMemory(List<Selection> selections) {
        currentRollMemory = selections;
    }

    private Rectangle[] splitScreen(int count) {
        //Returns an array of the display subsection dimensions for the category, and then the selection areas
        int displayBorder = 10;
        int xPoint = resultsDisplayDimensions.x;
        int yPoint = resultsDisplayDimensions.y;
        int displayWidth = (resultsDisplayDimensions.width / ((count > 0) ? count : 1)) - (displayBorder); // minimum width set to 1 or count


        int displayHeight = resultsDisplayDimensions.height * 36/100; //height set to 36% of display area.
        Rectangle[] displayAreas = new Rectangle[count * 2]; //doubles count for category + selection
            for (int i = 0; i < count; i++){
                displayAreas[i] = new Rectangle(xPoint + (i * displayWidth) + (i * displayBorder), yPoint, displayWidth, displayHeight);
                g2D.draw(displayAreas[i]);
            }
            //change height and yPoint
            yPoint = yPoint + displayHeight + displayBorder;
            displayHeight = resultsDisplayDimensions.height * 64/100 - displayBorder;
            for(int i = 0; i < count; i++) {
                displayAreas[i + count] = new Rectangle(xPoint + (i * displayWidth) + (i * displayBorder), yPoint, displayWidth, displayHeight);
                g2D.draw(displayAreas[i + count]);
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
