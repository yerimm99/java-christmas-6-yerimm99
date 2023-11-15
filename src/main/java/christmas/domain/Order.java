package christmas.domain;

import christmas.domain.enums.EventBadge;
import christmas.domain.event.GiftItem;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<OrderItem> orderItems;
    private int totalAmount;
    private int totalBenefitAmount;
    private List<GiftItem> giftItems;
    private EventBadge eventBadge;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.totalAmount = 0;
        this.totalBenefitAmount = 0;
        this.giftItems = new ArrayList<>();
        this.eventBadge = EventBadge.NONE;
    }

    public void addOrderItem(OrderItem orderItem) {
        if (!orderItems.contains(orderItem)) {
            orderItems.add(orderItem);
            calculateTotalAmount();
        }
    }

    public void applyDiscount(int discountAmount) {
        totalBenefitAmount += discountAmount;
    }

    public void addGiftItem(GiftItem giftItem) {
        giftItems.add(giftItem);
    }

    public void setEventBadge(EventBadge eventBadge) {
        this.eventBadge = eventBadge;
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getTotalBenefitAmount() {
        return totalBenefitAmount;
    }

    public List<GiftItem> getGiftItems() {
        return new ArrayList<>(giftItems);
    }

    public EventBadge getEventBadge() {
        return eventBadge;
    }

    public int getPayAmount() {
        return totalAmount - totalBenefitAmount + calculateTotalGiftItemPrice();
    }

    public List<OrderItem> getOrderedItems() {
        return orderItems;
    }

    private void calculateTotalAmount() {
        totalAmount = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    public int countDessertMenus() {
        return orderItems.stream().filter(OrderItem::isDessert).mapToInt(OrderItem::getQuantity).sum();
    }

    public int countMainMenus() {
        return orderItems.stream().filter(OrderItem::isMain).mapToInt(OrderItem::getQuantity).sum();
    }

    public int calculateTotalGiftItemPrice() {
        return giftItems.stream().mapToInt(giftItem -> giftItem.getPrice() * giftItem.getQuantity()).sum();
    }
}
