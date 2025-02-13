package services;

import controllers.BookEditMenu;
import controllers.BookFilteringMenu;
import controllers.BookSortingOptions;
import controllers.MenuManager;
import entities.Book;
import entities.User;
import exceptions.NotExistenceChoice;

import java.io.*;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class BooksManager {
    private static final Scanner scanner = new Scanner(System.in);

    /* Add a Book to User`s Library */
    public static Book addBook() {
        User currentUser = UsersManager.getCurrentUser();

        System.out.println("Input title of the book: ");
        String title = scanner.nextLine();

        System.out.println("Input Author Name of the book: ");
        String authorName = scanner.nextLine();

        System.out.println("Input genre of the book: ");
        String genre = scanner.nextLine();

        System.out.println("Input Publishing Year of the book: ");
        int publishingYear = scanner.nextInt();
        scanner.nextLine();

        boolean finishedReading;
        System.out.println("Have you read the book?(Y/N)");
        String isFinishedReading = scanner.nextLine();

        if(isFinishedReading.equals("Y")){
            finishedReading = true;
        } else if(isFinishedReading.equals("N")) {
            finishedReading = false;
        } else {
            System.out.println("Invalid Option! On book state");
            return null;
        }

        if (!checkNewBookPropertiesValidness(title, publishingYear)) return null;
        Book newBook = new Book(title, authorName, genre, publishingYear, finishedReading);
        currentUser.getBooks().add(newBook);
        System.out.println("Book added!");
        return newBook;
    }

    /* View list of User`s Books */
    public static void viewAllBooks() {
        User loggedUser = UsersManager.getCurrentUser();

        if (loggedUser.getBooks().isEmpty()) {
            System.out.println("There are no books in your Library.");
        } else {
            for (Book book : loggedUser.getBooks()) {
                System.out.println(book);
            }
        }
    }

    /* View sorted list of User`s Books */
    public static void sortBooks(BookSortingOptions sortingOptions) {
        User loggedUser = UsersManager.getCurrentUser();

        switch (sortingOptions) {
            case BY_TITLE_ASC:
                Comparator<Book> bookComparatorByTitleASC = (book1, book2) -> book1.getTitle().compareTo(book2.getTitle());
                loggedUser.getBooks().sort(Comparator.comparing(Book::getTitle).thenComparing(bookComparatorByTitleASC));
                String compareByTitleASCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByTitleASCOutput);
                break;
            case BY_TITLE_DESC:
                Comparator<Book> bookComparatorByTitleDESC = (book1, book2) -> book2.getTitle().compareTo(book1.getTitle());
                loggedUser.getBooks().sort(Comparator.comparing(Book::getTitle).thenComparing(bookComparatorByTitleDESC).reversed());
                String compareByTitleDESCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByTitleDESCOutput);
                break;
            case BY_PUBLISHED_YEAR_ASC:
                Comparator<Book> bookComparatorByYearASC = (year1, year2) -> Integer.compare(year1.getYear(), year2.getYear());
                loggedUser.getBooks().sort(Comparator.comparing(Book::getYear).thenComparing(bookComparatorByYearASC));
                String compareByYearASCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByYearASCOutput);
                break;
            case BY_PUBLISHED_YEAR_DESC:
                Comparator<Book> bookComparatorByYearDESC = (year1, year2) -> Integer.compare(year2.getYear(), year1.getYear());
                loggedUser.getBooks().sort(Comparator.comparing(Book::getYear).thenComparing(bookComparatorByYearDESC).reversed());
                String compareByYearDESCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByYearDESCOutput);
                break;
            case GO_BACK:
                break;
        }
    }

    /* View filtered list of User`s Books */
    public static void filterBooks(BookFilteringMenu bookFilteringMenu) {
        User loggedUser = UsersManager.getCurrentUser();

        switch (bookFilteringMenu) {
            case BookFilteringMenu.BY_AUTHOR:
                String displayAllAuthors = loggedUser.getBooks().stream().map(Book::getAuthorName).distinct().collect(Collectors.joining(", "));

                System.out.println("Here all the Authors in your library, please choose one you want to filter by: " + displayAllAuthors);
                String chosenAuthorName = scanner.nextLine();

                String filteredBooksByAuthor = loggedUser.getBooks().stream()
                        .filter(book -> book.getAuthorName()
                                .equalsIgnoreCase(chosenAuthorName))
                        .map(Book::toString)
                        .collect(Collectors.joining("\n"));
                System.out.println(filteredBooksByAuthor);
                break;
            case BookFilteringMenu.BY_GENRE:
                String displayAllGenres = loggedUser.getBooks().stream().map(Book::getGenre).distinct().collect(Collectors.joining(", "));

                System.out.println("Here all the Genres in your library, please choose one you want to filter by: " + displayAllGenres);
                String chosenGenre = scanner.nextLine();

                String filteredBooksByGenre = loggedUser.getBooks().stream()
                        .filter(book -> book.getGenre()
                                .equalsIgnoreCase(chosenGenre))
                        .map(Book::toString)
                        .collect(Collectors.joining("\n"));

                System.out.println(filteredBooksByGenre);
                break;
            case BookFilteringMenu.BY_READ_BOOKS:

                String isFinishedReading = loggedUser.getBooks().stream()
                        .filter(Book::isFinishedReading)
                        .map(Book::toString)
                        .collect(Collectors.joining(""));

                System.out.println(isFinishedReading);
            case BookFilteringMenu.GO_BACK:
                break;
        }
    }

    /* Delete a Book */
    public static void deleteBook() {
        User loggedUser = UsersManager.getCurrentUser();

        System.out.println("Please, enter title of the Book You want to delete: ");

        String bookToRemove = scanner.nextLine();

        try {
            for (Book book : loggedUser.getBooks()) {
                if (book.getTitle().equals(bookToRemove)) {
                    loggedUser.getBooks().remove(book);
                    System.out.println("Book removed!");
                    break;
                } else {
                    System.out.println("There`s no book with this Title.");
                }
            }
        } catch (NotExistenceChoice e) {
            System.out.println(e);
        }

        UsersManager.saveUserToStorage();
    }

    /* Search Book by Title */
    public static void searchBook() {
        User loggedUser = UsersManager.getCurrentUser();

        if(!loggedUser.getBooks().isEmpty()){
            System.out.println("Please, enter title of the Book You want to search: ");
            String bookToSearch = scanner.nextLine();

    //        List<Book> foundBooks = loggedUser.getBooks().stream().filter(book -> book.getTitle().equals(bookToSearch)).toList();
    //        foundBooks.stream().forEach(System.out::println);

            Book bookSearchResult = loggedUser.getBooks().stream().filter(book -> book.getTitle().equals(bookToSearch)).findFirst().get();
            System.out.println(bookSearchResult);

        } else {
            System.out.println("You don`t have any books in your library.");
        }
    }

    /* Edit Book */
    public static void editBook(BookEditMenu bookEditMenu, Book bookToEdit){
        Scanner scanner = new Scanner(System.in);

        switch (bookEditMenu){
            case BookEditMenu.EDIT_TITLE:
                System.out.println("Please, enter the New title for the Book: ");
                String newBookTitle = scanner.nextLine();
                bookToEdit.setTitle(newBookTitle);
                UsersManager.saveUserToStorage();
                break;
            case BookEditMenu.EDIT_AUTHOR:
                System.out.println("Please, enter the New Author Name for the Book: ");
                String newBookAuthorName = scanner.nextLine();
                bookToEdit.setAuthorName(newBookAuthorName);
                UsersManager.saveUserToStorage();
                break;
            case BookEditMenu.EDIT_GENRE:
                System.out.println("Please, enter the New Genre for the Book: ");
                String newBookGenre = scanner.nextLine();
                bookToEdit.setGenre(newBookGenre);
                UsersManager.saveUserToStorage();
                break;
            case BookEditMenu.EDIT_PUBLISHING_YEAR:
                System.out.println("Please, enter the New Publishing Year for the Book: ");
                int newBookYear = scanner.nextInt();
                bookToEdit.setYear(newBookYear);
                UsersManager.saveUserToStorage();
                break;
            case BookEditMenu.EDIT_READ_STATE:
                System.out.println("Have you read this book? (Y/N): ");
                String newBookReadState = scanner.nextLine();

                if(newBookReadState.equalsIgnoreCase("Y")){
                    bookToEdit.setFinishedReading(true);
                } else if(newBookReadState.equalsIgnoreCase("N")) {
                    bookToEdit.setFinishedReading(false);
                } else {
                    System.out.println("Invalid Option!");
                }
                UsersManager.saveUserToStorage();
                break;
            case GO_BACK:
                break;
        }
    }

    /* Checking the properties of the Book being added */
    private static boolean checkNewBookPropertiesValidness(String inputtedTitle, int inputtedYear) {
        User loggedUser = UsersManager.getCurrentUser();
        int currentYear = Year.now().getValue();

        for (Book book : loggedUser.getBooks()) {
            if (book.getTitle().equals(inputtedTitle)) {
                System.out.println("Book with provided Title already exists.");
                return false;
            } else if (inputtedYear >= 4 && inputtedYear > currentYear) {
                System.out.println("Invalid year.");
                return false;
            }
        }
        return true;
    }

    private BooksManager() {
    }
}
