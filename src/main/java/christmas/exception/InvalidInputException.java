package christmas.exception;

import christmas.domain.Menu;
import christmas.domain.OrderItem;
import christmas.domain.enums.Type;
import java.util.List;
import java.util.Set;

public class InvalidInputException extends RuntimeException {
    public static void checkLength(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    public static int parseQuantity(String quantityStr) {
        try {
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    public static boolean isValidDate(int date) {
        return date >= 1 && date <= 31;
    }

    public static void validateMenuAndDuplicates(Menu menu, Set<String> orderedMenuNames, String menuName) {
        validateMenu(menu, menuName);
        validateDuplicates(orderedMenuNames, menuName);
    }


    public static void validateMenu(Menu menu, String menuName) {
        if (!menu.hasMenuItem(menuName)) {
            throw new IllegalArgumentException("[ERROR] 메뉴판에 없는 메뉴를 입력하셨습니다. 다시 입력해 주세요.");
        }
    }

    public static void validateDuplicates(Set<String> orderedMenuNames, String menuName) {
        if (!orderedMenuNames.add(menuName)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    public static void validateOrderItemAndQuantity(OrderItem orderItem, int totalItems) {
        InvalidInputException.validateOrderItem(orderItem);
        InvalidInputException.validateOrderQuantity(orderItem, totalItems);
    }

    public static void validateOrderItem(OrderItem orderItem) {
        if (orderItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 1 이상의 숫자만 입력 가능합니다. 다시 입력해 주세요.");
        }
    }

    public static void validateOrderQuantity(OrderItem orderItem, int totalItems) {
        if (totalItems + orderItem.getQuantity() > 20) {
            throw new IllegalArgumentException("[ERROR] 한 번에 최대 20개까지 주문할 수 있습니다. 다시 입력해 주세요.");
        }
    }

    public static void validateDrinkOnlyAndTotalPrice(List<OrderItem> orderItems) {
        InvalidInputException.validateDrinkOnly(orderItems);
        InvalidInputException.validateTotalPrice(orderItems);
    }

    public static void validateDrinkOnly(List<OrderItem> orderItems) {
        if (orderItems.stream().allMatch(oItem -> oItem.getMenuItem().getType() == Type.DRINK)) {
            throw new IllegalArgumentException("[ERROR] 음료만 주문할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public static void validateTotalPrice(List<OrderItem> orderItems) {
        int totalAmount = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        if (totalAmount < 10000) {
            throw new IllegalArgumentException("[ERROR] 주문 금액은 10,000원 이상이어야 합니다. 다시 입력해 주세요.");
        }
    }
}