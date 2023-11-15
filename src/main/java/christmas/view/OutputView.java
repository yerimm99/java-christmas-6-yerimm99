package christmas.view;

import christmas.domain.enums.EventBadge;
import christmas.domain.event.DiscountEvent;
import christmas.domain.event.GiftEvent;
import christmas.domain.OrderItem;
import christmas.domain.event.GiftItem;
import java.util.ArrayList;
import java.util.List;

public class OutputView {
    public static void printMenu(List<OrderItem> orderItems) {
        System.out.println("\n<주문 메뉴>");
        for (OrderItem orderItem : orderItems) {
            System.out.printf("%s %d개\n", orderItem.getMenuItem().getName(), orderItem.getQuantity());
        }
    }

    public static void printTotalAmount(int totalAmount) {
        System.out.println("\n<할인 전 총주문 금액>");
        System.out.printf("%,d원\n", totalAmount);
    }

    public static void printGiftItem(List<GiftItem> giftItems) {
        System.out.println("\n<증정 메뉴>");
        if (giftItems.isEmpty()) {
            System.out.println("없음");
            return;
        }
        for (GiftItem giftItem : giftItems) {
            System.out.printf("%s %d개\n", giftItem.getName(), giftItem.getQuantity());
        }
    }

    private static List<String> benefitHistory = new ArrayList<>();

    public static void addBenefit(String benefit) {
        benefitHistory.add(benefit);
    }

    public static void printBenefitHistory() {
        System.out.println("\n<혜택 내역>");
        for (String benefit : benefitHistory) {
            System.out.println(benefit);
        }
        if (benefitHistory.isEmpty()) {
            System.out.println("없음");
        }
    }

    public static void printTotalBenefitAmount(int totalBenefitAmount) {
        System.out.println("\n<총혜택 금액>");
        if (totalBenefitAmount > 0) {
            System.out.printf("-%,d원\n", totalBenefitAmount);
        } else if (totalBenefitAmount == 0) {
            System.out.println("0원");
        }
    }

    public static void printDiscountedAmount(int discountedAmount) {
        System.out.println("\n<할인 후 예상 결제 금액>");
        System.out.printf("%,d원\n", discountedAmount);
    }

    public static void printEventBadge(EventBadge eventBadge) {
        System.out.println("\n<12월 이벤트 배지>");
        System.out.println(eventBadge.getLabel());
    }

}

