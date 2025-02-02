package controllers;

import entities.Book;
import entities.User;
import services.BooksManager;
import services.UsersManager;

import java.util.Scanner;

public class MenuManager {
    public static void mainMenu(Scanner scanner) {
        Integer choice = null;
        MainMenuOptions selectedOption = null;

        UsersManager.getUsersList().add(new User("user1", "1111"));

        do {
            MenuManager.welcomeMessageMainMenu();
            choice = scanner.nextInt();
            selectedOption = MainMenuOptions.valueOf(choice);
            scanner.nextLine();

            switch (selectedOption) {
                case MainMenuOptions.REGISTER_USER:
                    UsersManager.registerUser();
                    break;
                case MainMenuOptions.LOGIN_USER:
                    UsersManager.logInUser();
                    if (UsersManager.getUserStatus()) MenuManager.accountMenu(scanner);
                    break;
                case MainMenuOptions.GET_ALL_USERS:
                    UsersManager.getUsers();
                    break;
                case MainMenuOptions.EXIT:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (selectedOption != MainMenuOptions.EXIT);
    }

    private static void accountMenu(Scanner scanner) {
        User loggedUser = UsersManager.getCurrentUser();
        Integer choice = null;
        AccountMenuOptions selectedOption = null;

        System.out.println("Welcome, " + loggedUser.getUsername() + " !");

        do {
            MenuManager.welcomeMessageAccountMenu();
            choice = scanner.nextInt();
            selectedOption = AccountMenuOptions.valueOf(choice);

            switch (selectedOption) {
                case AccountMenuOptions.ADD_BOOK:
                    Book book = BooksManager.addBook();
                    loggedUser.getBooks().add(book);
                    break;
                case AccountMenuOptions.VIEW_ALL_BOOK:
                    BooksManager.viewAllBooks();
                    break;
                case AccountMenuOptions.DELETE_BOOK:
                    BooksManager.deleteBook();
                    break;
                case AccountMenuOptions.SEARCH_BOOK:
                    BooksManager.searchBook();
                    break;
                case AccountMenuOptions.LOG_OUT:
//                    UsersManager.logout();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (selectedOption != AccountMenuOptions.LOG_OUT);
    }

    private static void welcomeMessageAccountMenu() {
        System.out.println("Please, choose the operation You want to do: ");
        System.out.println("====================================================================");
        System.out.println("1) Add Book");
        System.out.println("2) View all Books");
        System.out.println("3) Delete Book");
        System.out.println("4) Search Book");
        System.out.println("5) Log Out");
    }

    private static void welcomeMessageMainMenu() {
        System.out.println("\nPlease, choose the operation You want to do: ");
        System.out.println("====================================================================");
        System.out.println("1) Register");
        System.out.println("2) Log In");
        System.out.println("3) View all users");
        System.out.println("4) Exit \n");
    }

    private MenuManager() {
    }

    ;
}
