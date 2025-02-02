import controllers.MenuManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MenuManager.mainMenu(scanner);
    }
}