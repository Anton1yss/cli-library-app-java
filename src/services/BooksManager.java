package services;

import constants.AddBookQuestionsConstants;
import enums.BookEditMenuOptions;
import enums.BookFilteringMenu;
import enums.BookSortingOptions;
import entities.Book;
import entities.User;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class BooksManager {
    private final Scanner scanner = new Scanner(System.in);
    UsersManager usersManager = new UsersManager();

    /* Add a Book to User`s Library */
    public Book addBook() {
        User currentUser = usersManager.getCurrentUser();

        System.out.println(AddBookQuestionsConstants.INPUT_BOOK_TITLE);
        String title = scanner.nextLine();
        if(!isTitleUnique(title)) return null;
        
        System.out.println(AddBookQuestionsConstants.INPUT_BOOK_AUTHOR_NAME);
        String authorName = scanner.nextLine();

        System.out.println(AddBookQuestionsConstants.INPUT_BOOK_GENRE);
        String genre = scanner.nextLine();

        System.out.println(AddBookQuestionsConstants.INPUT_BOOK_PUBLISHING_YEAR);
        int publishingYear = scanner.nextInt();
        if(!isPublishingYearValid(publishingYear)) return null;
        scanner.nextLine();

        boolean finishedReading;
        System.out.println(AddBookQuestionsConstants.INPUT_BOOK_STATE);
        String isFinishedReading = scanner.nextLine();

        if(isFinishedReading.equals("Y")){
            finishedReading = true;
        } else if(isFinishedReading.equals("N")) {
            finishedReading = false;
        } else {
            System.out.println("Invalid Option! On book state");
            return null;
        }

        Book newBook = new Book(title, authorName, genre, publishingYear, finishedReading);
        currentUser.getBooks().add(newBook);
        System.out.println("Book added!");
        return newBook;
    }

    /* View list of User`s Books */
    public void viewAllBooks() {
        User loggedUser = usersManager.getCurrentUser();

        if (loggedUser.getBooks().isEmpty()) {
            System.out.println("There are no books in your Library.");
        } else {
            loggedUser.getBooks().forEach(System.out::println);
        }
    }

    /* View sorted list of User`s Books */
    public void sortBooks(BookSortingOptions sortingOptions) {
        User loggedUser = usersManager.getCurrentUser();

        switch (sortingOptions) {
            case BY_TITLE_ASC:
                loggedUser.getBooks().sort(Comparator.comparing(Book::getTitle));
                String compareByTitleASCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByTitleASCOutput);
                break;
            case BY_TITLE_DESC:
                loggedUser.getBooks().sort(Comparator.comparing(Book::getTitle).reversed());
                String compareByTitleDESCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByTitleDESCOutput);
                break;
            case BY_PUBLISHED_YEAR_ASC:
                loggedUser.getBooks().sort(Comparator.comparing(Book::getYear));
                String compareByYearASCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByYearASCOutput);
                break;
            case BY_PUBLISHED_YEAR_DESC:
                loggedUser.getBooks().sort(Comparator.comparing(Book::getYear).reversed());
                String compareByYearDESCOutput = loggedUser.getBooks().stream().map(Book::toString).collect(Collectors.joining("\n"));
                System.out.println(compareByYearDESCOutput);
                break;
            case GO_BACK:
                break;
            default:
                System.out.println("Invalid Option!");
        }
    }

    /* View filtered list of User`s Books */
    public void filterBooks(BookFilteringMenu bookFilteringMenu) {
        User loggedUser = usersManager.getCurrentUser();

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
            default:
                System.out.println("Invalid Option!");
        }
    }

    /* Delete a Book */
    public void deleteBook() {
        User loggedUser = usersManager.getCurrentUser();

        System.out.println("Please, enter title of the Book You want to delete: ");
        String bookToRemove = scanner.nextLine();

        boolean bookRemoved = loggedUser.getBooks().removeIf(book -> book.getTitle().equals(bookToRemove));

        if(bookRemoved){
            System.out.println("Book removed!");
        } else {
            System.out.println("There`s no book with this title.");
        }
    }

    /* Search Book by Title */
    public void searchBook() {
        User loggedUser = usersManager.getCurrentUser();

        if(!loggedUser.getBooks().isEmpty()){
            System.out.println("Please, enter title of the Book You want to search: ");
            String bookToSearch = scanner.nextLine();

            Book bookSearchResult = loggedUser.getBooks().stream().filter(book -> book.getTitle().equals(bookToSearch)).findFirst().get();
            System.out.println(bookSearchResult);

        } else {
            System.out.println("There`s no book with this title.");
        }
    }

    /* Edit Book */
    public void editBook(BookEditMenuOptions bookEditMenuOptions, Book bookToEdit){
        Scanner scanner = new Scanner(System.in);

        switch (bookEditMenuOptions){
            case BookEditMenuOptions.EDIT_TITLE:
                System.out.println("Please, enter the New title for the Book: ");
                String newBookTitle = scanner.nextLine();
                if(!isTitleUnique(newBookTitle)) bookToEdit.setTitle(newBookTitle);
                usersManager.saveUsersToStorage();
                break;
            case BookEditMenuOptions.EDIT_AUTHOR:
                System.out.println("Please, enter the New Author Name for the Book: ");
                String newBookAuthorName = scanner.nextLine();
                bookToEdit.setAuthorName(newBookAuthorName);
                usersManager.saveUsersToStorage();
                break;
            case BookEditMenuOptions.EDIT_GENRE:
                System.out.println("Please, enter the New Genre for the Book: ");
                String newBookGenre = scanner.nextLine();
                bookToEdit.setGenre(newBookGenre);
                usersManager.saveUsersToStorage();
                break;
            case BookEditMenuOptions.EDIT_PUBLISHING_YEAR:
                System.out.println("Please, enter the New Publishing Year for the Book: ");
                int newBookYear = scanner.nextInt();
                if(!isPublishingYearValid(newBookYear)) bookToEdit.setYear(newBookYear);
                usersManager.saveUsersToStorage();
                break;
            case BookEditMenuOptions.EDIT_READ_STATE:
                System.out.println("Have you read this book? (Y/N): ");
                String newBookReadState = scanner.nextLine();

                if(newBookReadState.equalsIgnoreCase("Y")){
                    bookToEdit.setFinishedReading(true);
                } else if(newBookReadState.equalsIgnoreCase("N")) {
                    bookToEdit.setFinishedReading(false);
                } else {
                    System.out.println("Invalid Option!");
                }
                usersManager.saveUsersToStorage();
                break;
            case GO_BACK:
                break;
        }
    }

    /* Checking the title uniqueness of the Book being added */
    private boolean isTitleUnique(String inputtedTitle) {
        User loggedUser = usersManager.getCurrentUser();
        for (Book book : loggedUser.getBooks()) {
            if (book.getTitle().equals(inputtedTitle)) {
                System.out.println("Book with provided Title already exists. Try again.");
                return false;
            } else if (book.getTitle().length() > 64) {
                System.out.println("The title is too long. Try again.");
            }
        }
        return true;
    }

    /* Checking the publishing year validness of the Book being added */
    private boolean isPublishingYearValid(int inputtedYear) {
        int currentYear = Year.now().getValue();
        if (inputtedYear < 4 || inputtedYear > currentYear) {
            System.out.println("Invalid year! Try again.");
            return false;
        }
        return true;
    }

    /* Search Book by Title */
    public Book searchBookByTitle(){
        User loggedUser = usersManager.getCurrentUser();

        if(!loggedUser.getBooks().isEmpty()){
            System.out.println("Please, enter title of the Book You want to search: ");
            String bookToSearch = scanner.nextLine();

            Book optionalBook = loggedUser.getBooks().stream().filter(book -> book.getTitle().equals(bookToSearch)).findFirst().orElse(null);

            if(optionalBook != null){
                return optionalBook;
            } else {
                System.out.println("There`s no book with this title.");
                return null;
            }
        } else {
            System.out.println("Your library is empty, so you cannot edit the book.");
            return null;
        }
    }

    public BooksManager() {
    }
}
