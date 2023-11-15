package christmas.domain.event;

import christmas.service.DateUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class SpecialDiscountEvent extends DiscountEvent {
    public SpecialDiscountEvent() {
        super("특별 할인",
                "이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인",
                1, // 시작일
                31, // 종료일
                1000, // 초기 할인 금액
                0 // 할인 증분 (고정값)
        );
    }

    @Override
    public int calculateDiscount(int day, int totalAmount) {
        return getInitialDiscountAmount();
    }
    @Override
    public boolean isApplicable(int visitDate) {
        return DateUtil.isSpecialDiscount(visitDate);
    }

}
