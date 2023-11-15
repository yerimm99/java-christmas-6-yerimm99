package christmas;

import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.OrderItem;
import christmas.domain.event.*;
import christmas.service.Planner;
import christmas.view.InputView;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        // 메뉴 생성
        Menu menu = new Menu();

        // 날짜 및 주문 메뉴 입력 받기
        int date = InputView.readDate();
        List<OrderItem> orderItems = InputView.readOrderItems(menu);

        // 주문 생성
        Order order = createOrder(orderItems);

        // 이벤트 생성
        Event event = createDecemberEvent(order);

        // 이벤트 적용 및 결과 출력
        Planner planner = new Planner(event, order);
        planner.applyEventsAndPrintResult(date);
    }

    private static Event createDecemberEvent(Order order) {
        Event event = new Event("12월 이벤트", "2023-12-01 to 2023-12-31");

        // 할인 이벤트 생성
        event.addDiscountEvent(new ChristmasDiscountEvent());
        event.addDiscountEvent(new SpecialDiscountEvent());
        event.addDiscountEvent(new WeekdayDiscountEvent(order));
        event.addDiscountEvent(new WeekendDiscountEvent(order));

        // 증정 이벤트 생성
        GiftItem champagne = new GiftItem("샴페인", 25000, 1);
        event.addGiftEvent(new GiftEvent(120000, champagne));

        return event;
    }

    private static Order createOrder(List<OrderItem> orderItems) {
        Order order = new Order();
        orderItems.forEach(order::addOrderItem);
        return order;
    }
}
