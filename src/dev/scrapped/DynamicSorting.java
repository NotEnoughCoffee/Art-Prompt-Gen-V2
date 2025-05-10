package dev.scrapped;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicSorting {



    public static void main(String[] args) {
        //dimensions of text box
        int width = 160;
        int height = 79;

        //    DONT FORGET TO CHANGE NUMBER OF {} to match LINE COUNT
        //    YOU GOTTA ALSO CHANGE LINE COUNT AND THE AMOUNT OF int[x][] in reversed initiliazation AND RESORT the elements until you add backwards

        //UNIT TEST FOR BALANCER?? //
//
//        int[][] testArray =
//                {new int[]{4,3,5,1,3,2,4,3,  5, 8,3,4,2},
//                        {},
//                        {},
//                        {},
//                        {},
//                        {},
//                        {},
//                        {},
//                        {},
//                        {}};

        int[][] testArray = new int[10][];
        testArray[0] = new int[]{4,3,5,1,3,2,4,3,  5, 8,3,4,2};
        for(int i = 1; i < testArray.length; i++) {
            testArray[i] = new int[]{};
        }
        System.out.println(Arrays.deepToString(testArray));
        int[] spacesCount = new int[]{7,0,0,0,0,0,0,0,0,0};
        int lineCount = 10;

        int[][] filledArray = fillArray(testArray, lineCount);
        spacesCount = spacesCountUpdate(filledArray);
        System.out.println("////////////////");
        int[][] firstBalancedArray = balance(filledArray, spacesCount, lineCount);

        int[][] reversedBalancedArray = reverser(firstBalancedArray);

        System.out.println("=============");
        System.out.println(Arrays.deepToString(firstBalancedArray));
        System.out.println("=============");
        System.out.println(Arrays.deepToString(reversedBalancedArray));
        System.out.println("=============");



//
//        int[][] reverseBalancedArrayManual =
//                new int[10][];
//        reverseBalancedArrayManual[0] = new int[]{2,4,3};
//        reverseBalancedArrayManual[1] = new int[]{8};
//        reverseBalancedArrayManual[2] = new int[]{5};
//        reverseBalancedArrayManual[3] = new int[]{3};
//        reverseBalancedArrayManual[4] = new int[]{4,2};
//        reverseBalancedArrayManual[5] = new int[]{3};
//        reverseBalancedArrayManual[6] = new int[]{1};
//        reverseBalancedArrayManual[7] = new int[]{5};
//        reverseBalancedArrayManual[8] = new int[]{3};
//        reverseBalancedArrayManual[9] = new int[]{4};
//
////
//
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("running reverse");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");

        System.out.println(Arrays.deepToString(reversedBalancedArray));
        spacesCount = spacesCountUpdate(reversedBalancedArray);
        int[][] reBalancedArray = balance(reversedBalancedArray, spacesCount, lineCount);
        int[][] correctedReBalancedArray = reverser(reBalancedArray); // array has been sorted forwards and back -> now it should move onto testing with a method that takes the String and splits it based on the Method's results
        //also needs a method to convert that string into a int[][] to begin with

        // END BALANCER UNIT TEST//

        //REVERSER STANDALONE UNIT TEST//

//        int[][] testArray = {new int[]{1,2,3,4,5,6,7,8,9}, new int[]{4,3,5,1,3,2}, new int[]{9,8,7,6,5}, new int[]{3,2}, new int[]{0,1,1,0,0,7}};
//
//        int[][] reveredTest = reverser(testArray);
//        System.out.println(Arrays.deepToString(testArray));
//        System.out.println("=============");
//        System.out.println(Arrays.deepToString(reveredTest));

        //REVERSER END//

        /////// stringtoINT ARRAY TEST:///////////

        String alot = "Far far away behind the word mountains far from the countries Vokalia and Consona there live the blind texts Separated they live in Bookmarks grove right at the coast of the Semantics a large language ocean A small river named Duden flows by their place and supplies it with the necessary regelia It is a paradise country in which roasted parts of sentences fly into your mouth Even the all-powerful Pointing has no control about the blind texts it is an almost odd life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar The Big Oxmox advised her not to do so because there were thousands of bad Commas wild Question Marks and devious Semikoli but the Little Blind Text didn’t listen She packed her seven versalia put her initial into the belt and made herself on the way When she reached the first hills of the Italic Mountains she had a last view back on the skyline of her hometown Bookmarks grove the headline of Alphabet Village and the subline of her own road the Line Lane Pityful a rethoric question ran over her cheek then";

        //Notes: Large size of above text gives issue. But the program works at a line of about 20 words just fine. Which... is way more than what should be going into this in the first place.


        //intended use is for the prompt title bar; and displaying small category names and selection choices which are usually 1-2; no more than 5-6 words

        //i think what is happening is the array is so unbalanced to start that by the time it loads the back end of the array it just cancels out before it fixes anything. so when it gets to the mid point, the back of the array is super full, and the front is loaded with singluar words per line.

        //perhaps... evening out the array first could fix that issue.

        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println();
//        String testString = "I hope no one makes a string this large because boy this thing sure had better be able to handle it";
        //testString = alot;
        String testString = "The Title Of The Prompt GARY";
        int testStringLineCount = 3;
        //int[][] smalltester = evenOutArray(alot, testStringLineCount);
        int[][] createdArray = stringToIntArrays(testString,testStringLineCount);
        System.out.println(testString);
        System.out.println(Arrays.deepToString(createdArray));
        int[] spacesCount2 = spacesCountUpdate(createdArray);
        int[][] filledArray2 = fillArray(createdArray,testStringLineCount);
        //int[] spacesCount2 = spacesCountUpdate(smalltester);
        spacesCount2 = spacesCountUpdate(filledArray2);
        int[][] balancedArray2 = balance(filledArray2, spacesCount2, testStringLineCount);

//        List<String> finalSplitTEST = intArrayToStringList(balancedArray2,testString);
//        finalSplitTEST.forEach(System.out::println);



        int[][] reversedArray2 = reverser(balancedArray2);
        spacesCount2 = spacesCountUpdate(reversedArray2);
        int[][] rebalancedArray2 = balance(reversedArray2, spacesCount2, testStringLineCount);
        int[][] fixedBalancedArray2 = reverser(rebalancedArray2);
        System.out.println(Arrays.deepToString(fixedBalancedArray2));

        //now turn array into string
        List<String> finalSplit = intArrayToStringList(fixedBalancedArray2,testString);
        finalSplit.forEach(System.out::println);

        System.out.println("=====================");
        int[][] anotherTest = evenOutArray(testString,testStringLineCount);
        List<String> straightTest = intArrayToStringList(anotherTest,testString);
        straightTest.forEach(System.out::println);
        //int[][] smalltester = evenOutArray(alot, 7);
//        for(int[] ints : smalltester) {
//            System.out.println(Arrays.toString(ints));
//        }

    }


    //NEW//

    //String<->int[][] Conversion

    public static List<String> intArrayToStringList (int[][] rebalancedArray, String originalString) {
        List<String> returnList = new ArrayList<>();
        String[] words = originalString.split(" ");
        int counter = 0;

        for(int i = 0; i < rebalancedArray.length; i++) {
            StringBuilder wordsOnThisLine = new StringBuilder();
            for(int j = 0; j < rebalancedArray[i].length; j++){
                wordsOnThisLine.append(words[counter]).append(" ");
                counter++;
            }
            wordsOnThisLine.setLength(wordsOnThisLine.length() -1);
            returnList.add(i, String.valueOf(wordsOnThisLine));

        }



        return returnList;

    }

    public static int[][] evenOutArray (String string, int lineCount) {
        String[] words = string.split(" ");
        int wordCount = words.length; // number of words we are evenly distributing
        int counter = 0; // will keep track of words added to array
        int avgWordsPerLine = wordCount / lineCount;
        boolean isRemainder = false;
        int remainder = 0;
        if( wordCount % lineCount != 0 ) { // i.e there is something left over.
            remainder = wordCount % lineCount; // add one extra word to the first i < remainder "lines in the loop
            isRemainder = true;
        }

        int[][] returnArray = new int[lineCount][];
        for(int i = 0; i < lineCount; i++) {
            StringBuilder currentLine = new StringBuilder();
            for(int k = 0; k < avgWordsPerLine; k++) {
                //adds average number of words to the current line
                currentLine.append(words[counter]).append(" ");
                counter++;
            }
            if(isRemainder && remainder > 0) {
                //if flag is triggered, and remainder count is still > 0; an extra word will be added to the line.
                //all the words end up in the array?
                currentLine.append(words[counter]).append(" ");
                counter++;
                remainder--;
            }
            System.out.println("Counter = " + counter + " || Words Count = " + wordCount);

            //once line is built it is fed into overloaded function below, and a singular array is made.
            //loop repeated until all lines are filled will hopefully no words remaining
            returnArray[i] = stringToIntArrays(String.valueOf(currentLine)).clone();
        }

        return returnArray;
    }

    public static int[] stringToIntArrays(String string) {
        int[][] tempArrays  = stringToIntArrays(string, 1);

        return tempArrays[0].clone();
    }

    public static int[][] stringToIntArrays (String string, int lineCount) {
        //this returns a int[0] = { filled array where each int is the length of each word in the string} and all other {} are empty based on linecount. linecount needs a check that it is at least 2
        String[] split = string.split(" ");
        int[][] returnArray =  new int[lineCount][];
        int[] letterCount = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            letterCount[i] = split[i].length();
        }
        returnArray[0] = letterCount.clone();
        for(int i = 1; i < returnArray.length; i++) {
            returnArray[i] = new int[]{};
        }
        return  returnArray;
    }




    //ArraySetup:
    public static int[][] fillArray(int[][] lineArray, int lineCount) {
        //takes an int[][] where int[0] is filled and the rest of the elements are empty, and moves one into each array based on lineCount
        System.out.println("Received array to be filled: ");
        for(int[] ints : lineArray) {
            printArray(ints);
        }
        System.out.println("/////////////////////////////");

        int[][] outputArray = lineArray; //make copy for output
        boolean emptyElements = true;
        int i = lineCount; //iterator (starting at last element of array)

        while(emptyElements) {
            if(!emptyElementCheck(outputArray[i-1])) {
                //if the array before this one is not empty, take last element

                //the array before this array is not empty, AND the first array
                if(outputArray[0].length >= 1) { //as long as the first element in the array has two words; take a word
                    int[] previousArray = outputArray[i-1];
                    int[] newPreviousArray;

                    int[] currentArray;

                    int lastInt = previousArray[previousArray.length -1];
                    int newArrayLength = outputArray[i].length + 1;

                    currentArray = new int[newArrayLength];
                    currentArray[0] = lastInt;

                    for (int j = 1; j < newArrayLength; j++) {
                        currentArray[j] = outputArray[i][j-1];
                    }
                    outputArray[i] = currentArray;

                    newPreviousArray = Arrays.copyOf(previousArray, previousArray.length - 1);

                    outputArray[i-1] = newPreviousArray;

                    System.out.println(Arrays.deepToString(outputArray));
                }else {
                    System.out.println("Error I don't think this should be reached");
                }
            } //if we took the last word from the previous element or it was already empty:
            if(emptyElementCheck(outputArray[i-1])){ //the array before this one was empty, move to its position, and try again
                i--;

            } else if(i != lineCount) { //if this is not the last element in array
                i = lineCount;
            }

            emptyElements = false;
            for(int[] ints : outputArray) {
                //checks if any element in the array is 'empty'
                if(emptyElementCheck(ints)) {
                    emptyElements = true;
                    break;
                }
            } //if emptyElements found loop repeats, otherwise outputArray is set and returned.
        }

        return outputArray;
    }
    //in theory this works, and fills the entire array so no elements are 'empty

    //Balancer -< run the array forwards and then backwards through this method
    public static int[][] balance(int[][] lineArray, int[] spacesCount, int lineCount) {
        int[][] alteredArray = lineArray;
        int[] spaces = spacesCount;
        int lastLine = lineCount - 1;
        int[] currentLine = alteredArray[alteredArray.length - 1];
        int lastLineSum = getSum(currentLine) + spaces[spaces.length - 1];
        int firstLineSum = getSum(alteredArray[0]) + spaces[0];
        boolean passFlag = false;
        boolean overwriteFlag = true;
        int previousLineSum = 0;

        int[][] saveArray = new int[alteredArray.length][];
        while (lastLineSum < firstLineSum || !passFlag) {

            for(int i = lastLine; i > 0; i--) { //i = last index of lines we are looking at

                for(int K = 0; K < lineCount - 1; K++) {
                    i = lastLine;
                    System.out.println(" *** START : " + lastLine + " *** ");

                    for (int p = 0; p < alteredArray.length; p++) {
                        saveArray[p] = alteredArray[p].clone();
                    }

                    previousLineSum = getSum(alteredArray[lastLine - 1]) + spaces[lastLine - 1];

                    spaces = spacesCountUpdate(alteredArray);
                    lastLineSum = getSum(alteredArray[lastLine]) + spaces[lastLine];
                    firstLineSum = getSum(alteredArray[0]) + spaces[0];
                    System.out.println("last line " + lastLine + " and sum: " + lastLineSum + " and  first sum: " + firstLineSum + " at loop start");

                    if (lastLineSum > firstLineSum) {  //something in this cause infinite loop

                        System.out.println("TRIGGERED----------------------------------START----------------------->");
                        System.out.println(previousLineSum);
                        System.out.println(lastLineSum + " > " + firstLineSum);
                        System.out.println("============================");

                        //winner of the trigger also needs to be recursively tested so that it cnan be saved. or tested before its changed on the next loop
//                            overwriteFlag = false;
                        if (previousLineSum <= lastLineSum) {
                            //revert last array change

                            for (int p = 0; p < saveArray.length; p++) {
                                alteredArray[p] = saveArray[p].clone();
                            }
                            //alteredArray = saveArray;
                            overwriteFlag = false;
                            lastLine--;
                            i = lastLine;
                        }else {
                            for (int p = 0; p < alteredArray.length; p++) {
                                saveArray[p] = alteredArray[p].clone();

                            }

                        }//else array stays same
//                            i--;
                        System.out.println(Arrays.deepToString(alteredArray));
                        System.out.println("====================");
//                            passFlag = true;
                        i = lastLine;
                        lastLine--;
                        break;  //I think this made infinite loop. But now it goes directly to the DONT KNOW WHY IM HERE STATEMENT
                    }
                    System.out.println(alteredArray[K].length + " > 1 && K: " + K + " < " + lastLine);

                    //" I dont know why Im here triggers because the below doesnt pass:"
                    //this happens because K.length = 1;
                    // so because that is triggered, it just skips down to the next 'lastline'
                    // that sets i = 1; causing this to loop endlessly if it isnt there
                    // that causes the i loop to exit because i = 0; and the while loop moves us on to the next line

                    if (alteredArray[K].length > 1 && K < lastLine) {
                        previousLineSum = getSum(alteredArray[lastLine - 1]) + spaces[lastLine - 1];
                        if(overwriteFlag) {
                            for (int p = 0; p < alteredArray.length; p++) {
                                saveArray[p] = alteredArray[p].clone();

                            }

                        }
                        overwriteFlag = true;
                        currentLine = new int[alteredArray[K + 1].length + 1];
                        //sets up next line's array and moves an int from the end of this array to the next
                        currentLine[0] = alteredArray[K][alteredArray[K].length - 1];
                        for (int m = 1; m < currentLine.length; m++) {
                            currentLine[m] = alteredArray[K + 1][m - 1];
                        }
                        alteredArray[K] = Arrays.copyOf(alteredArray[K], alteredArray[K].length - 1);
                        alteredArray[K + 1] = currentLine;

                        //Altered array has been updated with moved word.

                        spaces = spacesCountUpdate(alteredArray);
                        lastLineSum = getSum(alteredArray[lastLine]) + spaces[lastLine];
                        firstLineSum = getSum(alteredArray[0]) + spaces[0];


                        System.out.println(Arrays.deepToString(alteredArray));
                        System.out.println("firstSum = " + firstLineSum);
                        System.out.println("lastSum = " + lastLineSum);
                        System.out.println("----------");

                        //sums recalculated

                        //needs to test if the last line is now longer otherwise keep going
                        if (lastLineSum > firstLineSum) {
                            System.out.println("TRIGGERED--------------------------------------------------------->");
                            System.out.println(previousLineSum + " <= " + lastLineSum);
                            System.out.println(lastLine);
                            System.out.println("============================");

                            //winner of the trigger also needs to be recursively tested so that it cnan be saved. or tested before its changed on the next loop
//                            overwriteFlag = false;
                            if (previousLineSum <= lastLineSum) {
                                //revert last array change
                                for (int p = 0; p < saveArray.length; p++) {
                                    alteredArray[p] = saveArray[p].clone();
                                }
                                //alteredArray = saveArray;

                                //overwriteFlag = false;
                                lastLine--;
                                i = lastLine;
                            }else {
                                for (int p = 0; p < alteredArray.length; p++) {
                                    saveArray[p] = alteredArray[p].clone();
                                }


                            }//else array stays same
//                            i--;
                            System.out.println(Arrays.deepToString(alteredArray));
                            System.out.println("====================");
//                            passFlag = true;
                            i = lastLine + 1;
                            break;
                        }

                    } else if(K >= lastLine) {
                        System.out.println("DDSFFDSFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                        //lastLine--;

                        System.out.println(Arrays.deepToString(alteredArray));
                        for (int p = 0; p < alteredArray.length; p++) {
                            saveArray[p] = alteredArray[p].clone();
                        }

                        //overwriteFlag = false;

                    }else{
                        System.out.println("                                                                        Don't know why I'm here");
                        //I think i am here because K has reached the end of the moves, and doesnt start loop back properly?
                        //if says that if this int Array K
                        System.out.println("J = " + K + ", I = " + i);
//                        i--;
                        //lastLine--;
                        i = 1;
                        break;
                    }

                }
            }
            lastLine--;
            System.out.println("I touched here " + lastLine);
            spaces = spacesCountUpdate(alteredArray);
            if(lastLine == 0 ) {
                lastLine++;
            }
            if(lastLine < 0) {

                break;
            }
            lastLineSum = getSum(alteredArray[lastLine]) + spaces[lastLine];
            firstLineSum = getSum(alteredArray[0]) + spaces[0];
        }
        return alteredArray;
    }


    public static int[][] reverser(int[][] toBeReversed) {
        int[][] reversed = new int[toBeReversed.length][];
        int counter = 0;
        for(int i = toBeReversed.length; i > 0; i--) {
            int[] clone = toBeReversed[i - 1].clone();
            int[] reversedClone = new int[clone.length];
            int cloneCounter = 0;
            for(int j = clone.length; j > 0; j--) {
                reversedClone[cloneCounter] = clone[j-1];
                cloneCounter++;
            }
            //reversed[counter] = toBeReversed[i-1];

            reversed[counter] = reversedClone.clone();
            counter++;
        }

        return reversed;
    }



    //important minor functions:
    public static int[] spacesCountUpdate(int[][] updatedArray) {
        int[] returnArray = new int[updatedArray.length];
        for(int i = 0; i < returnArray.length; i++) {
            returnArray[i] = updatedArray[i].length - 1;
        }
        return returnArray;
    }
    public static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }
    public static boolean emptyElementCheck(int[] ints) {
        //returns true if empty (ie sum of elements is 0)
        return Arrays.stream(ints).sum() == 0;
    }

    public static int getSum(int[] ints) {
        return Arrays.stream(ints).sum();
    }

}