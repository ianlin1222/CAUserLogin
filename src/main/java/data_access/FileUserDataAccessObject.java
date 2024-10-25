package data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import entity.User;
import entity.UserFactory;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface {

    private static final String HEADER = "username,password";
    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();
    private String currentUser;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) throws IOException {
        this.csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);

        if (csvFile.length() == 0) {
            save();
        }
        else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();
                if (!header.equals(HEADER)) {
                    throw new RuntimeException("Incorrect header: " + header);
                }
                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = col[headers.get("username")];
                    final String password = col[headers.get("password")];
                    accounts.put(username, userFactory.create(username, password));
                }
            }
        }
    }

    private void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();
            for (User user : accounts.values()) {
                writer.write(String.format("%s,%s", user.getName(), user.getPassword()));
                writer.newLine();
            }
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getName(), user);
        try {
            save();
        }
        catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public void changePassword(User user) {
        accounts.put(user.getName(), user);
        try {
            save();
        }
        catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public void setCurrentUser(String name) {
        this.currentUser = name;
    }

    @Override
    public String getCurrentUser() {
        return this.currentUser;
    }
}
