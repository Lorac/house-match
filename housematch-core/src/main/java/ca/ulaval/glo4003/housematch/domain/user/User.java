package ca.ulaval.glo4003.housematch.domain.user;

public class User {
    private String username;
    private String email;
    private String password;
    private UserRole role;

    public User() {
    }

    public User(final String username, final String email, final String password, final UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
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

    public void validatePassword(String password) {
        if (this.password.compareTo(password) != 0) {
            throw new InvalidPasswordException("Password does not match.");
        }
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
