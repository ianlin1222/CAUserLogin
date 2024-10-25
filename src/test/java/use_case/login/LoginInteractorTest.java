import org.junit.Test;
import static org.junit.Assert.*;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import use_case.login.*;

public class LoginInteractorTest {

    @Test
    public void successUserLoggedInTest() {
        // Arrange: Create input data and user repository (DAO)
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Create a user factory and store "Paul" in the repository
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // Assert: No user should be logged in initially
        assertNull(userRepository.getCurrentUser());

        // Create a success presenter to verify the test case
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());  // Ensure the logged-in user is "Paul"
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        // Act: Create the interactor and execute the login use case
        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);

        // Assert: Check that "Paul" is now the logged-in user
        assertEquals("Paul", userRepository.getCurrentUser());
    }
}
