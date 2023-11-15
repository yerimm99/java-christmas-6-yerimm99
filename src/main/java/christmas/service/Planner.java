package christmas.service;

import christmas.domain.enums.EventBadge;
import christmas.domain.event.ChristmasDiscountEvent;
import christmas.domain.event.DiscountEvent;
import christmas.domain.event.Event;
import christmas.domain.event.GiftEvent;
import christmas.domain.Order;
import christmas.view.OutputView;
import java.time.LocalDate;

public class Planner {
    private Event event;
    private Order order;

    public Planner(Event event, Order order) {
        this.event = event;
        this.order = order;
    }

    public void applyEvents(int visitDate) {
        if (order.getTotalAmount() >= 10000) {
            applyDiscountEvents(visitDate);
            applyGiftEvents();
            calculateEventBadge();
        }
    }

    private void applyDiscountEvents(int visitDate) {
        for (DiscountEvent discountEvent : event.getDiscountEvents()) {
            if (discountEvent.isApplicable(visitDate)) {
                int discountAmount = discountEvent.calculateDiscount(visitDate, order.getTotalAmount());
                order.applyDiscount(discountAmount);
                recordBenefit(discountEvent, discountAmount);
            }
        }
    }

    private void recordBenefit(DiscountEvent discountEvent, int discountAmount) {
        if (discountAmount > 0) {
            String benefitMessage = discountEvent.getName() + ": -" + discountAmount + "원";
            OutputView.addBenefit(benefitMessage);
        }
    }


    private void applyGiftEvents() {
        for (GiftEvent giftEvent : event.getGiftEvents()) {
            int targetAmount = giftEvent.getTargetAmount();
            if (order.getTotalAmount() >= targetAmount) {
                // 총 주문 금액이 기준 금액 이상인 경우에만 증정 이벤트 적용
                order.addGiftItem(giftEvent.getGiftItem());
                order.applyDiscount(giftEvent.getGiftItem().getPrice());
                OutputView.addBenefit("증정 이벤트: -" + giftEvent.getGiftItem().getPrice() + "원");
            }
        }
    }

    private void calculateEventBadge() {
        int totalBenefitAmount = order.getTotalBenefitAmount();

        if (totalBenefitAmount >= 20000) {
            order.setEventBadge(EventBadge.SANTA);
        } else if (totalBenefitAmount >= 10000) {
            order.setEventBadge(EventBadge.TREE);
        } else if (totalBenefitAmount >= 5000) {
            order.setEventBadge(EventBadge.STAR);
        } else if (totalBenefitAmount < 5000) {
            order.setEventBadge(EventBadge.NONE);
        }
    }

    public void printResult() {
        // 결과 출력
        OutputView.printMenu(order.getOrderedItems());
        OutputView.printTotalAmount(order.getTotalAmount());
        OutputView.printGiftItem(order.getGiftItems());
        OutputView.printBenefitHistory();
        OutputView.printTotalBenefitAmount(order.getTotalBenefitAmount());
        OutputView.printDiscountedAmount(order.getPayAmount());
        OutputView.printEventBadge(order.getEventBadge());
    }

    public void applyEventsAndPrintResult(int visitDate) {
        applyEvents(visitDate);  // 이벤트 적용
        printResult();  // 결과 출력
    }
}
