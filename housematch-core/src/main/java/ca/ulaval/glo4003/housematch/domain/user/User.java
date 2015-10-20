package ca.ulaval.glo4003.housematch.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;

import ca.ulaval.glo4003.housematch.domain.address.Address;
import ca.ulaval.glo4003.housematch.domain.property.Property;
import ca.ulaval.glo4003.housematch.domain.property.PropertyNotFoundException;
import ca.ulaval.glo4003.housematch.utils.StringHasher;

public class User {
    private StringHasher stringHasher;

    private String username;
    private String email;
    private String passwordHash;
    private UserRole role;
    private UUID activationCode;
    private Boolean activated = false;
    private List<Property> propertiesForSale = new ArrayList<>();
    private Address address;

    public User(final StringHasher stringHasher, final String username, final String email, final String password, final UserRole role) {
        this.stringHasher = stringHasher;
        this.username = username;
        this.email = email;
        this.passwordHash = stringHasher.hash(password);
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UUID getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(UUID activationCode) {
        this.activationCode = activationCode;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Property> getPropertiesForSale() {
        return propertiesForSale;
    }

    public void setPropertiesForSale(List<Property> properties) {
        this.propertiesForSale = properties;
    }

    public void validatePassword(String password) throws InvalidPasswordException {
        if (!this.passwordHash.equals(stringHasher.hash(password))) {
            throw new InvalidPasswordException("Password does not match.");
        }
    }

    public Boolean hasRole(UserRole role) {
        return this.role.equals(role);
    }

    public void updateEmail(String email) {
        this.email = email;
        activated = false;
    }

    public void activate() {
        activated = true;
        activationCode = null;
    }

    public void addPropertyForSale(Property property) {
        propertiesForSale.add(property);
    }

    public Property getPropertyForSaleByHashCode(int hashCode) throws PropertyNotFoundException {
        try {
            return propertiesForSale.stream().filter(p -> p.hashCode() == hashCode).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new PropertyNotFoundException(String.format("Cannot find property with hashcode '%s' belonging to this user.", hashCode));
        }
    }

    @Override
    public int hashCode() {
        return username.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        User user = (User) obj;
        return new EqualsBuilder().append(username.toLowerCase(), user.getUsername().toLowerCase()).isEquals();
    }

    public boolean usernameEquals(String username) {
        return this.username.equalsIgnoreCase(username);
    }
}
