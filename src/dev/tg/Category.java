package dev.tg;
import java.util.List;
public record Category(String name, List<Selection> selections) {
    //Record containing all the Selection choices within each category
    @Override
    public String toString() {
        return "Category: { %s } Selections: %s".formatted(name,selections);
    }
}