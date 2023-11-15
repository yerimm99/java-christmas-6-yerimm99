package christmas.domain.event;

import christmas.service.DateUtil;
import java.time.LocalDate;

public class ChristmasDiscountEvent extends DiscountEvent {
    public ChristmasDiscountEvent() {
        super("크리스마스 디데이 할인",
                "크리스마스가 다가올수록 날마다 할인 금액이 100원씩 증가",
                1, // 시작일
                25, // 종료일
                1000, // 초기 할인 금액
                100 // 할인 증분
        );
    }

    @Override
    public boolean isApplicable(int visitDate) {
        return visitDate >= getStartDay() && visitDate <= getEndDay();
    }

    @Override
    public int calculateDiscount(int dayOfMonth, int totalAmount) {
        if (!isApplicable(dayOfMonth)) {
            return 0;
        }

        int discountAmount = getInitialDiscountAmount();
        int dailyDiscount = getDiscountIncrement();

        // 할인 금액 계산
        for (int i = 0; i < dayOfMonth - 1; i++) {
            discountAmount += dailyDiscount;
        }

        // 총 주문 금액보다 큰 할인 금액이 계산되면 총 주문 금액으로 조정
        return Math.min(totalAmount, discountAmount);
    }
}
