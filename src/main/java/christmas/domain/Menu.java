package christmas.domain;

import static christmas.domain.enums.Type.APPETIZER;
import static christmas.domain.enums.Type.DESSERT;
import static christmas.domain.enums.Type.DRINK;
import static christmas.domain.enums.Type.MAIN;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> appetizers;
    private List<MenuItem> mains;
    private List<MenuItem> desserts;
    private List<MenuItem> drinks;

    public Menu() {
        initializeAppetizers();
        initializeMains();
        initializeDesserts();
        initializeDrinks();
    }

    public List<MenuItem> getAppetizers() {
        return appetizers;
    }

    public List<MenuItem> getMains() {
        return mains;
    }

    public List<MenuItem> getDesserts() {
        return desserts;
    }

    public List<MenuItem> getDrinks() {
        return drinks;
    }

    private void initializeAppetizers() {
        appetizers = new ArrayList<>();
        appetizers.add(new MenuItem("양송이수프", 6000, APPETIZER));
        appetizers.add(new MenuItem("타파스", 5500, APPETIZER));
        appetizers.add(new MenuItem("시저샐러드", 8000, APPETIZER));
    }

    private void initializeMains() {
        mains = new ArrayList<>();
        mains.add(new MenuItem("티본스테이크", 55000, MAIN));
        mains.add(new MenuItem("바비큐립", 54000, MAIN));
        mains.add(new MenuItem("해산물파스타", 35000, MAIN));
        mains.add(new MenuItem("크리스마스파스타", 25000, MAIN));
    }

    private void initializeDesserts() {
        desserts = new ArrayList<>();
        desserts.add(new MenuItem("초코케이크", 15000, DESSERT));
        desserts.add(new MenuItem("아이스크림", 5000, DESSERT));
    }

    private void initializeDrinks() {
        drinks = new ArrayList<>();
        drinks.add(new MenuItem("제로콜라", 3000, DRINK));
        drinks.add(new MenuItem("레드와인", 60000, DRINK));
        drinks.add(new MenuItem("샴페인", 25000, DRINK));
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> allMenuItems = new ArrayList<>();
        allMenuItems.addAll(appetizers);
        allMenuItems.addAll(mains);
        allMenuItems.addAll(desserts);
        allMenuItems.addAll(drinks);
        return allMenuItems;
    }

    public boolean hasMenuItem(String menuName) {
        // 주어진 메뉴명이 메뉴판에 있는지 확인
        return appetizers.stream().anyMatch(item -> item.getName().equals(menuName)) ||
                mains.stream().anyMatch(item -> item.getName().equals(menuName)) ||
                desserts.stream().anyMatch(item -> item.getName().equals(menuName)) ||
                drinks.stream().anyMatch(item -> item.getName().equals(menuName));
    }

}
