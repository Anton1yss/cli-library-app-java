package services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class StoringUsersManager {
    private static final String FILE_NAME = "UsersStorage.ser";

    public static void saveUserToStorage() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            objectOutputStream.writeObject(UsersManager.getUsersList());
            objectOutputStream.close();

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error, user hasn`t been added to storage.");
        }
    }
}
