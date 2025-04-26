package dev.tg;

import java.util.List;

public record Category(String name, List<Selection> selections) {
}
