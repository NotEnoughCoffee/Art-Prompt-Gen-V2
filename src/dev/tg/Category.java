package dev.tg;
import java.util.List;
public class Category {
    //Class that contains all the Selection choices within each category
    String name;
    List<Selection> selections;
    boolean enabled;
    public Category(String name, List<Selection> selections) {
        this.name = name;
        this.selections = selections;
        enabled = true;
    }

    @Override
    public String toString() {
        return "Category: { %s } Selections: %s".formatted(name,selections);
    }
}