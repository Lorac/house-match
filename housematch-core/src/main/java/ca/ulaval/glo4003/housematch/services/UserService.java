package ca.ulaval.glo4003.housematch.services;

import ca.ulaval.glo4003.housematch.domain.user.User;
import ca.ulaval.glo4003.housematch.domain.user.UserRepository;
import ca.ulaval.glo4003.housematch.domain.user.UserRole;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserRole> getRegistrableUserRoles() {
        List<UserRole> userRoles = Arrays.asList(UserRole.values());

        return userRoles.stream().filter(UserRole::isRegistrable).collect(Collectors.toList());
    }
}
