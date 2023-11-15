package christmas.domain.event;

import christmas.domain.Order;
import christmas.utils.DateUtil;

public class WeekendDiscountEvent extends DiscountEvent {
    private final Order order;

    public WeekendDiscountEvent(Order order) {
        super("주말 할인",
                "주말에는 메인 메뉴를 메뉴 1개당 2,023원 할인",
                1, // 시작일
                31, // 종료일
                2023, // 초기 할인 금액
                0 // 할인 증분 (고정값)
        );
        this.order = order;
    }

    @Override
    public boolean isApplicable(int visitDate) {
        return DateUtil.isWeekend(visitDate);
    }

    @Override
    public int calculateDiscount(int day, int totalAmount) {
        // 평일에는 디저트 메뉴를 메뉴 1개당 2,023원 할인
        int MainCount = order.countMainMenus();
        return MainCount * getInitialDiscountAmount();
    }

}
