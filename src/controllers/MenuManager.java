package controllers;

import entities.Book;
import entities.User;
import exceptions.NotExistenceChoice;
import services.BooksManager;
import services.UsersManager;

import java.util.Scanner;

public class MenuManager {
    public static void mainMenu(Scanner scanner) {
        Integer choice = null;
        MainMenuOptions selectedOption = null;

        User user1 = new User("user1", "1111");
        UsersManager.getUsersList().add(user1);
        user1.getBooks().add(new Book("The Catcher in the Rye", "JD Salinger", "novel", 1951, false));
//        user1.getBooks().add(new Book("Harry Potter and the Goblet of Fire", "JK Rowling", "fantasy", 2000, false));
//        user1.getBooks().add(new Book("The richest man in babylon", "George Samuel Clason", "self-help", 1926, true));

        do {
            try {
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
                        System.out.println("Exited!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid option!");
            }
        } while (selectedOption != MainMenuOptions.EXIT);
    }

    private static void accountMenu(Scanner scanner) {
        User loggedUser = UsersManager.getCurrentUser();
        Integer choice = null;
        AccountMenuOptions selectedOption = null;

        do {
            try {
                MenuManager.welcomeMessageAccountMenu();
                choice = scanner.nextInt();
                selectedOption = AccountMenuOptions.valueOf(choice);

                switch (selectedOption) {
                    case AccountMenuOptions.ADD_BOOK:
                        Book book = BooksManager.addBook();
                        if (book != null) loggedUser.getBooks().add(book);
                        break;
                    case AccountMenuOptions.VIEW_ALL_BOOK:
                        BooksManager.viewAllBooks();
                        scanner.nextLine();
                        sortOrFilterMessageMenu(scanner);
                        break;
                    case AccountMenuOptions.DELETE_BOOK:
                        BooksManager.deleteBook();
                        break;
                    case AccountMenuOptions.SEARCH_BOOK:
                        BooksManager.searchBook();
                        break;
                    case AccountMenuOptions.LOG_OUT:
                        UsersManager.logOutUser();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Option!");
            }
        } while (selectedOption != AccountMenuOptions.LOG_OUT);
    }

    private static void sortingBookMenu(Scanner scanner) {
        Integer choice = null;
        BookSortingMenuOptions selectedOption = null;

        do {
            try {
                MenuManager.sortBooksMessageMenu();
                choice = scanner.nextInt();
                selectedOption = BookSortingMenuOptions.valueOf(choice);

                switch (selectedOption) {
                    case BY_TITLE_ASC:
                        BooksManager.sortBooks(BookSortingOptions.BY_TITLE_ASC);
                        break;
                    case BY_TITLE_DESC:
                        BooksManager.sortBooks(BookSortingOptions.BY_TITLE_DESC);
                        break;
                    case BY_PUBLISHED_YEAR_ASC:
                        BooksManager.sortBooks(BookSortingOptions.BY_PUBLISHED_YEAR_ASC);
                        break;
                    case BY_PUBLISHED_YEAR_DESC:
                        BooksManager.sortBooks(BookSortingOptions.BY_PUBLISHED_YEAR_DESC);
                        break;
                    case GO_BACK:
                        BooksManager.sortBooks(BookSortingOptions.GO_BACK);
                        break;
                }
            } catch (NotExistenceChoice e) {
                System.out.println("Invalid Option!");
                System.out.println(e);
            }
        } while (selectedOption != BookSortingMenuOptions.GO_BACK);
    }

    private static void filterBooks(Scanner scanner) {
        Integer choice = null;
        BookFilteringMenu selectedOption = null;

        do {
            try {
                filterBooksMessageMenu();
                choice = scanner.nextInt();
                selectedOption = BookFilteringMenu.valueOf(choice);

                switch (selectedOption) {
                    case BY_AUTHOR:
                        BooksManager.filterBooks(BookFilteringMenu.BY_AUTHOR);
                        break;
                    case BY_GENRE:
                        BooksManager.filterBooks(BookFilteringMenu.BY_GENRE);
                        break;
                    case BY_READ_BOOKS:
                        BooksManager.filterBooks(BookFilteringMenu.BY_READ_BOOKS);
                        break;
                    case GO_BACK:
                        BooksManager.filterBooks(BookFilteringMenu.GO_BACK);
                        break;
                }
            } catch (NotExistenceChoice e) {
                System.out.println("Invalid Option!");
                System.out.println(e);
            }
        } while (selectedOption != BookFilteringMenu.GO_BACK);
    }

    private static void welcomeMessageAccountMenu() {
        System.out.println("Account Menu || Please, choose the operation You want to do: ");
        System.out.println("====================================================================");
        System.out.println("1) Add Book");
        System.out.println("2) View all Books");
        System.out.println("3) Delete Book");
        System.out.println("4) Search Book");
        System.out.println("5) Log Out");
    }

    private static void welcomeMessageMainMenu() {
        System.out.println("\nMain Menu || Please, choose the operation You want to do: ");
        System.out.println("====================================================================");
        System.out.println("1) Register");
        System.out.println("2) Log In");
        System.out.println("3) View all users");
        System.out.println("4) Exit \n");
    }

    private static void sortBooksMessageMenu() {
        System.out.println("\nSorting || Please, choose how do You want to sort by: ");
        System.out.println("====================================================================");
        System.out.println("1) By Author ACS");
        System.out.println("2) By Author DESC");
        System.out.println("3) By Publishing Year ASC");
        System.out.println("4) By Publishing Year DESC");
        System.out.println("5) Go Back");
    }

    private static void filterBooksMessageMenu() {
        System.out.println("\nPlease, choose what You want to sort by: ");
        System.out.println("====================================================================");
        System.out.println("1) Author");
        System.out.println("2) Genre");
        System.out.println("3) Finished reading");
        System.out.println("4) Go Back");
    }

    private static void sortOrFilterMessageMenu(Scanner scanner) {
        User loggedUser = UsersManager.getCurrentUser();

        if (!loggedUser.getBooks().isEmpty()) {
            System.out.println("Do you want to Sort or Filter you books? (Y/N):");
            String answer = scanner.nextLine();

            if (answer.equals("Y")) {
                System.out.println("Please, choose Sort or Filter (S/F): ");

                String operationChoice = scanner.nextLine();

                if (operationChoice.equals("S")) {
                    sortingBookMenu(scanner);
                } else if (operationChoice.equals("F")) {
                    filterBooks(scanner);
                } else {
                    throw new NotExistenceChoice(operationChoice);
                }
            } else if (answer.equals("N")) {
                accountMenu(scanner);
            } else {
                throw new NotExistenceChoice(answer);
            }
        }
    }

    private MenuManager() {
    }
}
