package use_case.login;

import entity.User;

/**
 * DAO interface for the Login Use Case.
 * This interface defines methods for user data access, such as checking user existence,
 * saving a user, retrieving user information, and tracking the current logged-in user.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the given user to the data store.
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Retrieves a user with the given username.
     *
     * @param username the username to look up
     * @return the user associated with the given username
     */
    User get(String username);

    /**
     * Sets the current logged-in user.
     *
     * @param name the username of the current logged-in user
     */
    void setCurrentUser(String name);

    /**
     * Retrieves the username of the current logged-in user.
     *
     * @return the username of the current logged-in user, or null if no user is logged in
     */
    String getCurrentUser();
}
