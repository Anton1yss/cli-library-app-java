package controllers;

import entities.Book;
import entities.User;
import enums.*;
import exceptions.NotExistenceChoice;
import services.BooksManager;
import services.UsersManager;

import java.util.Scanner;

public class MenuManager {
    BooksManager booksManager = new BooksManager();
    UsersManager usersManager = new UsersManager();

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        MainMenuOptions selectedOption = null;

        do {
            try {
                MenuManager.welcomeMessageMainMenu();
                Integer choice = scanner.nextInt();
                selectedOption = MainMenuOptions.valueOf(choice);
                scanner.nextLine();

                switch (selectedOption) {
                    case MainMenuOptions.REGISTER_USER:
                        usersManager.registerUser();
                        usersManager.saveUsersToStorage();
                        break;
                    case MainMenuOptions.LOGIN_USER:
                        usersManager.logInUser();
                        if (usersManager.getUserStatus()) accountMenu(scanner);
                        break;
                    case MainMenuOptions.GET_ALL_USERS:
                        usersManager.getUsers();
                        break;
                    case MainMenuOptions.EXIT:
                        System.out.println("Exit completed!");
                        break;
                }
            } catch (NotExistenceChoice e){
                System.out.println("Invalid choice!");
                System.out.println(e);
            } catch (Exception c){
                System.out.println("Error occurred.");
                System.out.println(c);
            }
        } while (selectedOption != MainMenuOptions.EXIT);
    }

    private void accountMenu(Scanner scanner) {
        AccountMenuOptions selectedOption = null;

        do {
            try {


                MenuManager.welcomeMessageAccountMenu();
                Integer choice = scanner.nextInt();
                selectedOption = AccountMenuOptions.valueOf(choice);

                switch (selectedOption) {
                    case AccountMenuOptions.ADD_BOOK:
                        Book book = booksManager.addBook();
                        usersManager.saveUsersToStorage();
                        break;
                    case AccountMenuOptions.VIEW_ALL_BOOK:
                        booksManager.viewAllBooks();
                        scanner.nextLine();
                        sortOrFilterMessageMenu(scanner);
                        break;
                    case AccountMenuOptions.DELETE_BOOK:
                        booksManager.deleteBook();
                        usersManager.saveUsersToStorage();
                        break;
                    case AccountMenuOptions.SEARCH_BOOK:
                        booksManager.searchBook();
                        break;
                    case AccountMenuOptions.EDIT_BOOK:
                        editBookMenu(scanner);
                        break;
                    case AccountMenuOptions.LOG_OUT:
                        usersManager.logOutUser();
                        break;
                }
            } catch (NotExistenceChoice e){
                System.out.println("Invalid choice!");
                System.out.println(e);
            } catch (Exception c){
                System.out.println("Error occurred.");
                System.out.println(c);
            }
        } while (selectedOption != AccountMenuOptions.LOG_OUT);
    }

    private void sortingBookMenu(Scanner scanner) {
        BookSortingMenuOptions selectedOption = null;

        do {
            try {
                MenuManager.sortBooksMessageMenu();
                Integer choice = scanner.nextInt();
                selectedOption = BookSortingMenuOptions.valueOf(choice);

                switch (selectedOption) {
                    case BY_TITLE_ASC:
                        booksManager.sortBooks(BookSortingOptions.BY_TITLE_ASC);
                        break;
                    case BY_TITLE_DESC:
                        booksManager.sortBooks(BookSortingOptions.BY_TITLE_DESC);
                        break;
                    case BY_PUBLISHED_YEAR_ASC:
                        booksManager.sortBooks(BookSortingOptions.BY_PUBLISHED_YEAR_ASC);
                        break;
                    case BY_PUBLISHED_YEAR_DESC:
                        booksManager.sortBooks(BookSortingOptions.BY_PUBLISHED_YEAR_DESC);
                        break;
                    case GO_BACK:
                        booksManager.sortBooks(BookSortingOptions.GO_BACK);
                        break;
                    default:
                        System.out.println("Invalid Option!");
                }
            } catch (NotExistenceChoice e){
                System.out.println("Invalid choice!");
                System.out.println(e);
            } catch (Exception c){
                System.out.println("Error occurred.");
                System.out.println(c);
            }
        } while (selectedOption != BookSortingMenuOptions.GO_BACK);
    }

    private void filterBooksMenu(Scanner scanner) {
        BookFilteringMenu selectedOption = null;

        do {
            try {


                filterBooksMessageMenu();
                Integer choice = scanner.nextInt();
                selectedOption = BookFilteringMenu.valueOf(choice);

                switch (selectedOption) {
                    case BY_AUTHOR:
                        booksManager.filterBooks(BookFilteringMenu.BY_AUTHOR);
                        break;
                    case BY_GENRE:
                        booksManager.filterBooks(BookFilteringMenu.BY_GENRE);
                        break;
                    case BY_READ_BOOKS:
                        booksManager.filterBooks(BookFilteringMenu.BY_READ_BOOKS);
                        break;
                    case GO_BACK:
                        booksManager.filterBooks(BookFilteringMenu.GO_BACK);
                        break;
                    default:
                        System.out.println("Invalid Option!");
                }
            } catch (NotExistenceChoice e){
                System.out.println("Invalid choice!");
                System.out.println(e);
            } catch (Exception c){
                System.out.println("Error occurred.");
                System.out.println(c);
            }
        } while (selectedOption != BookFilteringMenu.GO_BACK);
    }

    public void editBookMenu(Scanner scanner) {
        BookEditMenuOptions selectedOption = null;
        Book bookToEdit = booksManager.searchBookByTitle();

        do {
            try {


                editBooksMessageMenu();
                Integer choice = scanner.nextInt();
                selectedOption = BookEditMenuOptions.valueOf(choice);

                switch (selectedOption) {
                    case EDIT_TITLE:
                        booksManager.editBook(BookEditMenuOptions.EDIT_TITLE, bookToEdit);
                        break;
                    case EDIT_AUTHOR:
                        booksManager.editBook(BookEditMenuOptions.EDIT_AUTHOR, bookToEdit);
                        break;
                    case EDIT_GENRE:
                        booksManager.editBook(BookEditMenuOptions.EDIT_GENRE, bookToEdit);
                        break;
                    case EDIT_PUBLISHING_YEAR:
                        booksManager.editBook(BookEditMenuOptions.EDIT_PUBLISHING_YEAR, bookToEdit);
                        break;
                    case EDIT_READ_STATE:
                        booksManager.editBook(BookEditMenuOptions.EDIT_READ_STATE, bookToEdit);
                        break;
                    case GO_BACK:
                        booksManager.editBook(BookEditMenuOptions.GO_BACK, bookToEdit);
                        break;
                    default:
                        System.out.println("Invalid Option!");
                }
            } catch (NotExistenceChoice e){
                System.out.println("Invalid choice!");
                System.out.println(e);
            } catch (Exception c){
                System.out.println("Error occurred.");
                System.out.println(c);
            }
        } while (selectedOption != BookEditMenuOptions.GO_BACK);
    }

    private void sortOrFilterMessageMenu(Scanner scanner) {
        User loggedUser = usersManager.getCurrentUser();

        if (!loggedUser.getBooks().isEmpty()) {
            System.out.println("Do you want to Sort or Filter you books? (Y/N):");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("Y")) {
                System.out.println("Please, choose Sort or Filter (S/F): ");
                String operationChoice = scanner.nextLine().trim();

                if (operationChoice.equalsIgnoreCase("S")) {
                    sortingBookMenu(scanner);
                } else if (operationChoice.equals("F")) {
                    filterBooksMenu(scanner);
                } else {
                    throw new NotExistenceChoice(operationChoice);
                }
            } else if (answer.equals("N")) {
                accountMenu(scanner);
            } else {
                throw new NotExistenceChoice(answer);
            }
        } else {
            System.out.println("You don`t have any books in your library.");
        }
    }

    private static void welcomeMessageAccountMenu() {
        System.out.println("Account Menu || Please, choose the operation do You want to do: ");
        System.out.println("====================================================================");
        System.out.println("1) Add Book");
        System.out.println("2) View all Books");
        System.out.println("3) Delete Book");
        System.out.println("4) Search Book");
        System.out.println("5) Edit Book");
        System.out.println("6) Log Out");
    }

    private static void welcomeMessageMainMenu() {
        System.out.println("\nMain Menu || Please, choose the operation do You want to do: ");
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
        System.out.println("\nPlease, choose what do You want to filter by: ");
        System.out.println("====================================================================");
        System.out.println("1) Author");
        System.out.println("2) Genre");
        System.out.println("3) Finished reading");
        System.out.println("4) Go Back");
    }

    public static void editBooksMessageMenu() {
        System.out.println("\nPlease, choose what do You want to edit to: ");
        System.out.println("====================================================================");
        System.out.println("1) Edit Title");
        System.out.println("2) Edit Author");
        System.out.println("3) Edit Genre");
        System.out.println("4) Edit Publishing Year");
        System.out.println("5) Edit Read State");
        System.out.println("6) Go Back");
    }

    public MenuManager() {
    }
}
