package christmas.domain.enums;

public enum EventBadge {
    STAR("별"),
    TREE("트리"),
    SANTA("산타"),
    NONE("없음"); // "없음" 항목 추가

    private final String label;

    EventBadge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
