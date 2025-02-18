package services;

import entities.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class LoadingUsersManager {
    private static final String FILE_NAME = "UsersStorage.ser";

    public static void loadUsersFromStorage() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));

            LinkedList<User> usersFromStorage = (LinkedList<User>) objectInputStream.readObject();

            UsersManager.getUsersList().addAll(usersFromStorage.stream().toList());

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("loadUsersFromStorage — IOException error.");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("loadUsersFromStorage — Exception error.");
        }
    }
}
