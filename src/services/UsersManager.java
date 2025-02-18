package services;

import controllers.MenuManager;
import entities.User;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

public class UsersManager {
    static private final Scanner scanner = new Scanner(System.in);

    private static final LinkedList<User> usersList = new LinkedList<User>();
    public static User currentUser;
    private static Boolean isUserLogged = false;

    public static LinkedList<User> getUsersList() {
        return UsersManager.usersList;
    }

    /* Register */
    public void registerUser() {
        System.out.println("Input unique Username: ");
        String username = scanner.nextLine();

        while (checkUsernameForDuplication(username) && (username != null)) {
            System.out.println("User with this nickname already exists. Try Again: ");
            username = scanner.nextLine();
        }

        System.out.println("Now create a password: ");
        String password = scanner.nextLine();

        while (password.length() != 4) {
            System.out.println("Password should have 4 digits");
            System.out.println("Try again: ");
            password = scanner.nextLine();
        }

        User user = new User(username, password);

        usersList.add(user);

        System.out.println("Registered!");
    }

    /* Log In */
    public void logInUser() {
        System.out.println("Please, enter your username: ");
        String username = scanner.nextLine();

        System.out.println("Now, enter your password: ");
        String password = scanner.nextLine();

        Optional<User> user = checkUserCredentials(username, password);

        if (user.isEmpty()) {
            System.out.println("The username or password is wrong.");
            UsersManager.isUserLogged = false;
        } else {
            System.out.println("\nLogin successful! Welcome, " + username + "!");
            UsersManager.currentUser = user.get();
            UsersManager.isUserLogged = true;
        }
    }

    /* Log out User */
    public void logOutUser() {
        UsersManager.currentUser = null;
        UsersManager.isUserLogged = false;
//        MenuManager.mainMenu();
        System.out.println("Logged Out!");
    }

    /* Save Users to Storage */
    public void saveUsersToStorage() {
        StoringUsersManager.saveUserToStorage();
    }

    /* Load Users from Storage */
    public void loadUsersFromStorage() {
        LoadingUsersManager.loadUsersFromStorage();
    }

    /* Display All Users */
    public void getUsers() {
        if (usersList.isEmpty()) {
            System.out.println("There`re no users.");
        } else {
            System.out.println("All the library users: ");
            usersList.stream().forEach(user -> System.out.println("- " + user.getUsername()));
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Boolean getUserStatus() {
        return UsersManager.isUserLogged;
    }

    private Optional<User> checkUserCredentials(String username, String password) {
        return usersList.stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst();
    }

    private boolean checkUsernameForDuplication(final String username) {
        return usersList.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public UsersManager() {
    }
}
