package ca.ulaval.glo4003.housematch.services;

import ca.ulaval.glo4003.housematch.domain.user.UserRole;

import java.util.ArrayList;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserCredentials(String username, String password) {
        User user = getUserByUsername(username);
        user.validatePassword(password);
    }

    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public void createUser(String username, String email, String password, UserRole role) {
        User user = new User(username, email, password, role);
        userRepository.persist(user);
    }

    public ArrayList<UserRole> getRegisterableUserRoles() {
        ArrayList<UserRole> registerableUserRoles = new ArrayList<UserRole>();
        for (UserRole userRole : UserRole.values()) {
            if (userRole.isRegisterable()) {
                registerableUserRoles.add(userRole);
            }
        }
        return registerableUserRoles;
    }
}
