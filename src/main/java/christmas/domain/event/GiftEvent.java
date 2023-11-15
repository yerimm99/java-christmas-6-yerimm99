package christmas.domain.event;

public class GiftEvent {
    private final int targetAmount;
    private final GiftItem giftItem;

    public GiftEvent(int targetAmount, GiftItem giftItem) {
        this.targetAmount = targetAmount;
        this.giftItem = giftItem;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public GiftItem getGiftItem() {
        return giftItem;
    }
}

