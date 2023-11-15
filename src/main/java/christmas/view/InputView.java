package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.Menu;
import christmas.domain.OrderItem;
import christmas.domain.enums.Type;
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
                if (isValidDate(date)) {
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

    private static boolean isValidDate(int date) {
        return date >= 1 && date <= 31;
    }

    private static List<OrderItem> parseOrderItems(String input, Menu menu) {
        List<OrderItem> orderItems = new ArrayList<>();
        Set<String> orderedMenuNames = new HashSet<>();
        int totalItems = 0;

        String[] items = input.split(",");
        for (String item : items) {
            String[] parts = item.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }

            String menuName = parts[0].trim(); // 메뉴 이름에서 공백 제거
            int quantity;
            try {
                quantity = Integer.parseInt(parts[1].trim()); // 수량에서 공백 제거
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            }

            // 주문 메뉴명이 메뉴판에 있는지 확인
            if (!menu.hasMenuItem(menuName)) {
                throw new IllegalArgumentException("[ERROR] 메뉴판에 없는 메뉴를 입력하셨습니다. 다시 입력해 주세요.");
            }

            // 주문 메뉴 중복 확인
            if (!orderedMenuNames.add(menuName)) {
                throw new IllegalArgumentException("[ERROR] 중복된 메뉴를 입력하셨습니다. 다시 입력해 주세요.");
            }

            // 주문 수량 확인
            if (quantity <= 0) {
                throw new IllegalArgumentException("[ERROR] 주문 수량은 1 이상의 숫자만 입력 가능합니다. 다시 입력해 주세요.");
            }
            // 주문 개수 확인
            totalItems += quantity;
            if (totalItems > 20) {
                throw new IllegalArgumentException("[ERROR] 한 번에 최대 20개까지 주문할 수 있습니다. 다시 입력해 주세요.");
            }

            // OrderItem.createOrderItem 메서드를 사용하여 OrderItem 생성
            OrderItem orderItem = OrderItem.createOrderItem(menuName, quantity, menu);
            orderItems.add(orderItem);
        }


        // 음료만 주문 확인
        if (orderItems.stream().allMatch(oItem -> oItem.getMenuItem().getType() == Type.DRINK)) {
            throw new IllegalArgumentException("[ERROR] 음료만 주문할 수 없습니다. 다시 입력해 주세요.");
        }

        return orderItems;
    }
}
