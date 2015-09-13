package ca.ulaval.glo4003.housematch.domain.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.validator.routines.EmailValidator;

public class User {
    private String username;
    private String email;
    private String password;
    private UserRole role;

    public User() {
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
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be blank.");
        }
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email cannot be blank.");
        } else if (!EmailValidator.getInstance(false).isValid(email)) {
            throw new IllegalArgumentException("The email format is not valid.");
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Password cannot be blank.");
        }
        this.password = password;
    }

    public void validatePassword(String password) {
        if (!this.password.equals(password)) {
            throw new InvalidPasswordException("Password does not match.");
        }
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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

    public boolean isUsernameEqual(String username) {
        return this.username.equalsIgnoreCase(username);
    }
}
