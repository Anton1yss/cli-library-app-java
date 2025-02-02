package services;

import entities.Book;
import entities.User;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BooksManager{
    private static final Scanner scanner = new Scanner(System.in);

    static public Book addBook(){
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

        return new Book(title, authorName, genre, publishingYear, finishedReading);
     }

    public static void viewAllBooks(){
        User loggedUser = UsersManager.getCurrentUser();

        if (loggedUser.getBooks().isEmpty()) {
            System.out.println("There is no book yet.");
        } else {
            String output = loggedUser.getBooks().stream().map(Book::getTitle).collect(Collectors.joining(","));
            System.out.println(output);
        }
    }

    public static void deleteBook(){
        User loggedUser = UsersManager.getCurrentUser();

        System.out.println("Please, enter title of the Book You want to delete");

        String bookTitleToRemove = scanner.nextLine();

        for (int i = 0; i < loggedUser.getBooks().size(); i++) {
            if (loggedUser.getBooks().get(i).getTitle().equals(bookTitleToRemove)) {
                loggedUser.getBooks().remove(i);

                System.out.println("Book removed");
                break;
            }
        }
    }

    public static void searchBook() {
        User loggedUser = UsersManager.getCurrentUser();

        System.out.println("Please, enter title of the Book You want to delete");

        String bookToSearch = scanner.nextLine();

        List<Book> foundBooks = loggedUser.getBooks().stream().filter(book -> book.getTitle().equals(bookToSearch)).toList();

        foundBooks.stream().forEach(System.out::println);
    }

    private BooksManager () {};
}
