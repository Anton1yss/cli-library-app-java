package services;

import controllers.MenuManager;
import entities.User;

import java.io.*;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class UsersManager {
    static private final Scanner scanner = new Scanner(System.in);

    private static final LinkedList<User> usersList = new LinkedList<User>();
    public static User currentUser;
    private static Boolean isUserLogged = false;

    public static LinkedList<User> getUsersList() {
        return UsersManager.usersList;
    }

    /* Register */
    public static void registerUser() {
        System.out.println("Input unique Username: ");
        String username = scanner.nextLine();

        while (UsersManager.checkUsernameForDuplication(username) && (username != null)) {
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
    }

    /* Log out User */
    public static void logOutUser() {
        UsersManager.currentUser = null;
        UsersManager.isUserLogged = false;
        MenuManager.mainMenu(scanner);
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

//    private static String GenerateUserID() {
//        LinkedList<String> userIDsList = new LinkedList<>();
//
//        File usersStorageFile = new File("UsersStorage.ser");
//
//        if(usersStorageFile.exists()){
//            try {
//                ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream(usersStorageFile));
//                userIDsList = (LinkedList<String>) objectInputStream.readObject();
//            } catch (Exception c){
//                System.out.println(c);
//            }
//        }
//
//        String userID;
//
//        do {
//            userID = UUID.randomUUID().toString();
//        } while (userIDsList.contains(userID));
//
//        return userID;
//    }

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

    public static void saveUserToStorage() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("UsersStorage.ser"));

            objectOutputStream.writeObject(UsersManager.getUsersList());
//            System.out.println("User added to storage!");
            objectOutputStream.close();

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error, user hasn`t been added to storage.");
        }
    }

//    public static void saveBookChangesToUserLibrary(){
//        User loggedUser = UsersManager.getCurrentUser();
//
//        try {
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("UsersStorage.ser"));
//            LinkedList<User> usersFromStorage = (LinkedList<User>) objectInputStream.readObject();
//
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("UsersStorage.ser"));
//
//            for(User user : usersFromStorage){
//                if(loggedUser.equals(user)){
//                }
//            }
//
//            System.out.println("User added to storage!");
//            objectOutputStream.close();
//
//        } catch (IOException e) {
//            System.out.println(e);
//            System.out.println("Error, user hasn`t been added to storage.");
//        } catch (Exception c){
//            System.out.println(c);
//        }
//    }

    public static void loadUsersFromStorage() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("UsersStorage.ser"));

            LinkedList<User> usersFromStorage = (LinkedList<User>) objectInputStream.readObject();

            for (User user : usersFromStorage) {
                UsersManager.getUsersList().add(user);
            }



        } catch (IOException e) {
            System.out.println(e);
            System.out.println("loadUsersFromStorage — IOException error.");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("loadUsersFromStorage — Exception error.");
        }
    }

    private UsersManager() {
    }
}
