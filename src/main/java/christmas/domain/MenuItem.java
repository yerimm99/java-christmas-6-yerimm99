package christmas.domain;

import christmas.domain.enums.Type;

public class MenuItem {
    private String name;
    private int price;
    private Type type;

    public MenuItem(String name, int price, Type type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }
}
