package dev.tg;

public record Selection() {

    //needs a category string, text string, rarity ranking int (1 2 3) can be null


    // file format:
    // A1: Category || A2: Text || A3: # || A4: Text || A5: # || repeat...
    //category is pulled out, and all elements in that row are added to an array of Selection to the with the cat name.

    // last time I rolled the categories to be rolled, then rolled that category.


    //nodfbdfgsfvf




    //instead
    //make a category class as well, that is a record Category(Selection[] category)

    //an array is prebuilt with a capacity of 10 (10 categories) Category[] where each is null.
            //assign the Category[i] values by reading the line, and creating selection records for each, then adding them to
            //the Selection[] is an array of record Selections that are in that specific category.
            //this way you can roll categories by picking the first dimension of the array. then rolling the selected.
    //the file is pulled into a loop.
    // a scanner reads each line, each line is a category, and the category name is the first string in each line.
    //

}
