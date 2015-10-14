package ca.ulaval.glo4003.housematch.domain.property;

public enum PropertyStyle {
    CHALET("Chalet"),
    SPLIT_LEVEL("Split Level"),
    IGLOO("Igloo"),
    RANCH("Ranch"),
    BI_LEVEL("Bi-Level");

    private final String displayName;

    PropertyStyle(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
