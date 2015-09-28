package ca.ulaval.glo4003.housematch.domain.user;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class User {
    String username;
    String email;
    String password;
    UserRole role;
    boolean activated = false;

    User() {
        // Required for instanciation by reflection
    }

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

    public void validatePassword(String password) throws InvalidPasswordException {
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException("Password does not match.");
        }
    }

    public void validateActivation() throws UserNotActivatedException {
        if (!this.activated) {
            throw new UserNotActivatedException("User is not activated.");
        }
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean hasRole(UserRole role) {
        return this.role.equals(role);
    }

    public void activate() {
        this.activated = true;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
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
