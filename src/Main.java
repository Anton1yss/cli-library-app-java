import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        UsersManager users = new UsersManager();

        users.usersMap.put("user1", "1111");
        users.usersMap.put("user2", "1111");
        users.usersMap.put("user3", "1111");
        users.usersMap.put("user4", "1111");

        BooksManager booksManager = new BooksManager();
        booksManager.booksList.add(new Book("Harry Potter 1", "J. K. Rowling", "Fantasy", 1997, true));

        while(choice != 4){
            System.out.println("1) Register");
            System.out.println("2) Log In");
            System.out.println("3) View all users");
            System.out.println("4) Exit");

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    users.registerUser();
//                    booksManager.addBook();
                    break;
                case 2:
                    users.logInUser();
                    break;
                case 3:
                    users.getUsers();
//                    booksManager.viewBooks();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}