package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.Menu;
import christmas.domain.OrderItem;
import christmas.exception.InvalidInputException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputView {
    public static int readDate() {
        while (true) {
            try {
                System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
                int date = Integer.parseInt(Console.readLine());
                if (InvalidInputException.isValidDate(date)) {
                    return date;
                } else {
                    System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
            }
        }
    }

    public static List<OrderItem> readOrderItems(Menu menu) {
        while (true) {
            try {
                System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
                String input = Console.readLine();
                return parseOrderItems(input, menu);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static List<OrderItem> parseOrderItems(String input, Menu menu) {
        List<OrderItem> orderItems = new ArrayList<>();
        Set<String> orderedMenuNames = new HashSet<>();
        int totalItems = 0;

        String[] items = input.split(",");
        for (String item : items) {
            OrderItem orderItem = createOrderItem(item.trim(), menu, orderedMenuNames);
            InvalidInputException.validateOrderItemAndQuantity(orderItem, totalItems);
            orderItems.add(orderItem);
            totalItems += orderItem.getQuantity();
        }
        InvalidInputException.validateDrinkOnly(orderItems);
        return orderItems;
    }

    private static OrderItem createOrderItem(String item, Menu menu, Set<String> orderedMenuNames) {
        String[] parts = item.split("-");
        InvalidInputException.checkLength(parts);

        String menuName = parts[0].trim();
        int quantity = InvalidInputException.parseQuantity(parts[1].trim());

        InvalidInputException.validateMenuAndDuplicates(menu, orderedMenuNames, menuName);

        return OrderItem.createOrderItem(menuName, quantity, menu);
    }


}
