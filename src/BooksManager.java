import java.util.ArrayList;
import java.util.Scanner;

public class BooksManager{
    ArrayList<Book> booksList = new ArrayList<Book>();

    Book newBook = new Book(" ", " ", " ", 0, false);

    Scanner scanner = new Scanner(System.in);

    void addBook(){
        System.out.println("Input title of the book: ");
        newBook.title = scanner.nextLine();
        System.out.println("Input Author Name of the book: ");
        newBook.authorName = scanner.nextLine();
        System.out.println("Input genre of the book: ");
        newBook.genre = scanner.nextLine();
        System.out.println("Input Publishing Year of the book: ");
        newBook.publishingYear = scanner.nextInt();
        System.out.println("Have you already read the book?(True/False)");
        newBook.finishedReading = scanner.nextBoolean();
        booksList.add(new Book(newBook.title, newBook.authorName, newBook.genre, newBook.publishingYear, newBook.finishedReading));
    }



    void viewBooks(){
        for (Book book : booksList){
            System.out.println(book.toString());
        }
    }



}
