package ca.ulaval.glo4003.housematch.domain.user;

public enum UserRole {
    ADMINISTRATOR("Administrator", false),
    SELLER("Seller", true),
    BUYER("Buyer", true);

    private final String displayName;
    private final Boolean isPubliclyRegistrable;

    UserRole(final String displayName, final Boolean isPubliclyRegistrable) {
        this.displayName = displayName;
        this.isPubliclyRegistrable = isPubliclyRegistrable;
    }

    public Boolean isPubliclyRegistrable() {
        return this.isPubliclyRegistrable;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
