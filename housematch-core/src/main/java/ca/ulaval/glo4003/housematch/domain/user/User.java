package ca.ulaval.glo4003.housematch.domain.user;

import ca.ulaval.glo4003.housematch.domain.property.Property;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private UUID activationCode;
    private Boolean activated = false;
    private List<Property> propertyListings = new ArrayList<Property>();

    public User(final String username, final String email, final String password, final UserRole role) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setRole(role);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Property> getPropertyListings() {
        return propertyListings;
    }

    public void setPropertyListings(List<Property> propertyListings) {
        this.propertyListings = propertyListings;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);

    }

    public void validateActivation() throws UserNotActivatedException {
        if (!this.activated) {
            throw new UserNotActivatedException("User is not activated.");
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

    public void addPropertyListing(Property property) {
        propertyListings.add(property);
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
