package christmas.domain;

import christmas.domain.enums.Type;
import java.util.List;

public class OrderItem {
    private final MenuItem menuItem;
    private final int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    // 메뉴 이름으로 MenuItem을 찾아 반환하는 메서드
    public static OrderItem createOrderItem(String menuName, int quantity, Menu menu) {
        MenuItem menuItem = findMenuItemByName(menuName, menu.getAllMenuItems());

        return new OrderItem(menuItem, quantity);
    }


    // 메뉴 이름으로 MenuItem을 찾아 반환하는 메서드
    private static MenuItem findMenuItemByName(String menuName, List<MenuItem> menuItems) {
        for (MenuItem item : menuItems) {
            if (item.getName().equals(menuName)) {
                return item;
            }
        }
        return null;
    }

    public boolean isDessert() {
        return menuItem.getType() == Type.DESSERT;
    }

    public boolean isMain() {
        return menuItem.getType() == Type.MAIN;
    }
}


