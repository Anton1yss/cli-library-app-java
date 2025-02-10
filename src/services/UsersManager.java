package services;

import entities.User;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

public class UsersManager {
    static private final Scanner scanner = new Scanner(System.in);

    private static LinkedList<User> usersList = new LinkedList<User>();
    public static User currentUser;
    private static Boolean isUserLogged = false;

    public static LinkedList<User> getUsersList() {
        return UsersManager.usersList;
    }

    /* Register */
    public static void registerUser() {
        System.out.println("Input unique Username: ");
        String username = scanner.nextLine();

        while (UsersManager.checkUsernameForDuplication(username)) {
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
    public static void logInUser() {
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
        BooksManager.loadUserLibrary();
    }

    /* Log out User */
    public static void logOutUser() {
        BooksManager.saveBooksToStorage();
        UsersManager.isUserLogged = false;
        System.out.println("Logged Out, bye!");
    }

    /* Display All Users */
    public static void getUsers() {
        if (usersList.isEmpty()) {
            System.out.println("There`re no users.");
        } else {
            System.out.println("All the library users: ");
            usersList.stream().forEach(user -> System.out.println("- " + user.getUsername()));
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Boolean getUserStatus() {
        return UsersManager.isUserLogged;
    }

    private static Optional<User> checkUserCredentials(String username, String password) {
        return usersList.stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst();
    }

    private static boolean checkUsernameForDuplication(final String username) {
        return usersList.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private UsersManager() {
    }
}
