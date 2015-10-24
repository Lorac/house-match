package ca.ulaval.glo4003.housematch.domain.property;

public enum PropertyOwnershipType {
    SOLE_OWNERSHIP("Sole Ownership"),
    DIVIDED("Divided"),
    JOINT_TENANCY("Joint Tenancy"),
    TENANCY_IN_COMMON("Tenancy In Common"),
    COMMUNITY_PROPERTY("Community Property");

    private final String displayName;

    PropertyOwnershipType(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
