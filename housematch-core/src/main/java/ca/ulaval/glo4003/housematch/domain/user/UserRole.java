package ca.ulaval.glo4003.housematch.domain.user;

public enum UserRole {
    ADMINISTRATOR("Administrator", false),
    SELLER("Seller", true),
    BUYER("Buyer", true);

    private final String displayName;
    private final Boolean isRegistrable;

    UserRole(final String displayName, final Boolean isRegistrable) {
        this.displayName = displayName;
        this.isRegistrable = isRegistrable;
    }

    public Boolean isRegistrable() {
        return this.isRegistrable;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
