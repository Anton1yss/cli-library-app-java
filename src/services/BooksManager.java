package services;

import controllers.BookFilteringMenu;
import controllers.BookSortingOptions;
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
        System.out.println("Input title of the book: ");

        String title = scanner.nextLine();

        System.out.println("Input Author Name of the book: ");
        String authorName = scanner.nextLine();

        System.out.println("Input genre of the book: ");
        String genre = scanner.nextLine();

        System.out.println("Input Publishing Year of the book: ");
        int publishingYear = scanner.nextInt();

        System.out.println("Have you already read the book?(True/False)");
        boolean finishedReading = scanner.nextBoolean();

        scanner.nextLine();

        if (!checkNewBookPropertiesValidness(title, publishingYear)) return null;
        saveBooksToStorage();
        return new Book(title, authorName, genre, publishingYear, finishedReading);
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

        saveBooksToStorage();
    }

    /* Search Book by Title */
    public static void searchBook() {
        User loggedUser = UsersManager.getCurrentUser();

        System.out.println("Please, enter title of the Book You want to search: ");

        String bookToSearch = scanner.nextLine();

        List<Book> foundBooks = loggedUser.getBooks().stream().filter(book -> book.getTitle().equals(bookToSearch)).toList();

        foundBooks.stream().forEach(System.out::println);
    }

    /* Save User`s library to Storage File */
    public static void saveBooksToStorage() {
        User loggedUser = UsersManager.getCurrentUser();

        try {
            FileOutputStream fos = new FileOutputStream("UserLibraryStorage.ser");
            ObjectOutputStream objStr = new ObjectOutputStream(fos);

            objStr.writeObject(loggedUser.getBooks());
            objStr.close();

            System.out.println("Library`s state saved!");

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error, Library saving failed.");
        }
    }

    /* Load User`s Library */
    public static void loadUserLibrary() {
        User loggedUser = UsersManager.getCurrentUser();

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("UserLibraryStorage.ser"));
            List<Book> importedBook = (List<Book>) objectInputStream.readObject();

            for (Book book : importedBook) {
                if (!loggedUser.getBooks().contains(book)) {
                    loggedUser.getBooks().add(book);
                }
            }
            System.out.println("User`s Library loaded!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static boolean checkNewBookPropertiesValidness(String inputedTitle, int inputedYear) {
        User loggedUser = UsersManager.getCurrentUser();
        int currentYear = Year.now().getValue();

        for (Book book : loggedUser.getBooks()) {
            if (book.getTitle().equals(inputedTitle)) {
                System.out.println("Book with provided Title already exists.");
                return false;
            } else if (inputedYear >= 4 && inputedYear > currentYear) {
                System.out.println("Invalid year.");
                return false;
            }
        }
        return true;
    }

    private BooksManager() {
    }
}
