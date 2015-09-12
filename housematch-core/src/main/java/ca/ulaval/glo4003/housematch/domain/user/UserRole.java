package ca.ulaval.glo4003.housematch.domain.user;

public enum UserRole {
    ADMINISTRATOR("Administrator", false),
    SELLER("Seller", true),
    BUYER("Buyer", true);

    private final String displayName;
    private final Boolean isRegisterable;

    UserRole(final String displayName, final Boolean isRegisterable) {
        this.displayName = displayName;
        this.isRegisterable = isRegisterable;
    }

    public Boolean isRegisterable() {
        return this.isRegisterable;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
