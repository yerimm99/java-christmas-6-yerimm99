package christmas.domain.event;

import java.time.LocalDate;

public class DiscountEvent {
    private String name;
    private String description;
    private int startDay;
    private int endDay;
    private int initialDiscountAmount;
    private int discountIncrement;

    public DiscountEvent(String name, String description, int startDay, int endDay, int initialDiscountAmount, int discountIncrement) {
        this.name = name;
        this.description = description;
        this.startDay = startDay;
        this.endDay = endDay;
        this.initialDiscountAmount = initialDiscountAmount;
        this.discountIncrement = discountIncrement;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getInitialDiscountAmount() {
        return initialDiscountAmount;
    }

    public int getDiscountIncrement() {
        return discountIncrement;
    }

    public int calculateDiscount(int dayOfMonth, int totalAmount) {

        return Math.min(totalAmount, initialDiscountAmount);
    }

    public boolean isApplicable(int visitDay){
        return false;
    }
}
