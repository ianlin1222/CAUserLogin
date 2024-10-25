package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data.
 * This implementation does NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUser;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void changePassword(User user) {
        users.put(user.getName(), user);
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param name the username of the current user
     */
    @Override
    public void setCurrentUser(String name) {
        this.currentUser = name;
    }

    /**
     * Retrieves the username of the current logged-in user.
     *
     * @return the username of the current user, or {@code null} if no user is logged in
     */
    @Override
    public String getCurrentUser() {
        return this.currentUser;
    }
}
