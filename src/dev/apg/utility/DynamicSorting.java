package dev.apg.utility;

import java.util.Arrays;

public class DynamicSorting {



    public static void main(String[] args) {
        //dimensions of text box
        int width = 160;
        int height = 79;

        //    DONT FORGET TO CHANGE NUMBER OF {} to match LINE COUNT
        //    YOU GOTTA ALSO CHANGE LINE COUNT AND THE AMOUNT OF int[x][] in reversed initiliazation AND RESORT the elements until you add backwards

        //UNIT TEST FOR BALANCER?? //
//
        int[][] testArray =
                {new int[]{4,3,5,1,3,2,4,3,  5, 8,3,4,2},
                        {},
                        {},
                        {},
                        {},
                        {},
                        {},
                        {},
                        {},
                        {}};

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

    }


    //NEW//


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

                    //so if above if gets triggered. below if wont be because [k].length = 1 // needs to skip somehow
                    //if the line before this one is not lie[0] and k.length = 1

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