package christmas.domain.event;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private String eventPeriod;
    private List<DiscountEvent> discountEvents;
    private List<GiftEvent> giftEvents;

    public Event(String eventName, String eventPeriod) {
        this.eventName = eventName;
        this.eventPeriod = eventPeriod;
        this.discountEvents = new ArrayList<>();
        this.giftEvents = new ArrayList<>();
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventPeriod() {
        return eventPeriod;
    }

    public List<DiscountEvent> getDiscountEvents() {
        return discountEvents;
    }

    public List<GiftEvent> getGiftEvents() {
        return giftEvents;
    }

    // 할인 이벤트 추가
    public void addDiscountEvent(DiscountEvent discountEvent) {
        discountEvents.add(discountEvent);
    }

    // 증정 이벤트 추가
    public void addGiftEvent(GiftEvent giftEvent) {
        giftEvents.add(giftEvent);
    }


}
