package ca.ulaval.glo4003.housematch.domain.user;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class User {
    String username;
    String email;
    String password;
    String address;
    String postalCode;
    String city;
    String country;

    UserRole role;
    Integer activationCode;
    boolean activated = false;
    Integer modifcationCode;
    boolean pendingModification = false;
    String temporaryEmail;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void validatePassword(String password) throws InvalidPasswordException {
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException("Password does not match.");
        }
    }

    public Integer getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(Integer activationCode) {
        this.activationCode = activationCode;
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
        this.activationCode = null;
    }

    public boolean isActivated() {
        return activated;
    }

    public void startModification(Integer code) {
        this.pendingModification = true;
        this.modifcationCode = code;
    }

    public void endModification() {
        this.pendingModification = false;
        this.modifcationCode = null;
    }

    public Integer getModificationCode() {
        return modifcationCode;
    }

    public void setTemporaryMail(String email) {
        this.temporaryEmail = email;
    }

    public String getTemporaryMail() {
        return temporaryEmail;
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
