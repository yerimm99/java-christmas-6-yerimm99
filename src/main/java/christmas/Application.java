package christmas;

import christmas.domain.Menu;
import christmas.domain.OrderItem;
import christmas.domain.event.ChristmasDiscountEvent;
import christmas.domain.event.Event;
import christmas.domain.event.GiftEvent;
import christmas.domain.event.GiftItem;
import christmas.domain.Order;
import christmas.domain.event.SpecialDiscountEvent;
import christmas.domain.event.WeekdayDiscountEvent;
import christmas.domain.event.WeekendDiscountEvent;
import christmas.service.Planner;
import christmas.view.InputView;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        // 메뉴 생성
        Menu menu = new Menu();

        // 이벤트 생성
        Event event = new Event("12월 이벤트", "2023-12-01 to 2023-12-31");

        // 날짜 입력 받기
        int date = InputView.readDate();
        // 주문 메뉴 입력 받기
        List<OrderItem> orderItems = InputView.readOrderItems(menu);

        // 주문 생성
        Order order = new Order();  // 주문 객체 생성
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        // 할인 이벤트 생성
        ChristmasDiscountEvent christmasDiscount = new ChristmasDiscountEvent();
        SpecialDiscountEvent specialDiscount = new SpecialDiscountEvent();
        WeekdayDiscountEvent weekdayDiscount = new WeekdayDiscountEvent(order);
        WeekendDiscountEvent weekendDiscount = new WeekendDiscountEvent(order);

        // 증정 이벤트 생성
        GiftItem champagne = new GiftItem("샴페인", 25000, 1);
        GiftEvent giftEvent = new GiftEvent(100000, champagne);

        // 할인 및 증정 이벤트 추가
        event.addDiscountEvent(christmasDiscount);
        event.addDiscountEvent(specialDiscount);
        event.addDiscountEvent(weekdayDiscount);
        event.addDiscountEvent(weekendDiscount);
        event.addGiftEvent(giftEvent);

        //이벤트 적용
        Planner planner = new Planner(event, order);  // Planner 객체 생성
        planner.applyEventsAndPrintResult(date);
    }
}
