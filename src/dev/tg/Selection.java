package dev.tg;
public record Selection(String Category, String name, int rarity) {
    // excel user file format:
    // A1: Category Name || A2: Rarity # || A3: Selection 1 || A4: Selection 2 || A5: Selection 3 || repeat...
    //Selection are the options of each Category that will be randomly rolled as art prompts for the user
}