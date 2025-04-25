package dev.tg;

public record Selection(String Category, String name, int rarity) {
    // excel user file format:
    // A1: Category || A2: Rarity # || A3: Text || A4: Text || A5: Text || repeat...

    //Selection are the options that will be rolled within the category

    //an array is prebuilt with a capacity of 10 (10 categories) Category[] where each is null.

            // + will need to add checking if category already exists to add things in that line to the existing cat.

            //assign the Category[i] values by reading the line, and creating selection records for each, then adding them to
            //the Selection[] is an array of record Selections that are in that specific category.
            //this way you can roll categories by picking the first dimension of the array. then rolling the selected.
    //the file is pulled into a loop.
    // a scanner reads each line, each line is a category, and the category name is the first string in each line.
    //

}
