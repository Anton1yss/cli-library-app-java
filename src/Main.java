import controllers.MenuManager;
import services.UsersManager;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsersManager.loadUsersFromStorage();
        MenuManager.mainMenu(scanner);
    }
}