import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UsersManager {
    HashMap<String, String> usersMap = new HashMap<String, String>();
    String username, password;

    Scanner scanner = new Scanner(System.in);

    void registerUser(){
        System.out.println("Input unique Username: ");
        username = scanner.nextLine();
        System.out.println("Now create a password: ");
        password = scanner.nextLine();
        while (password.length() !=4){
            System.out.println("Password should have 4 digits");
            System.out.println("Try again: ");
            password = scanner.nextLine();
        }
        if(usersMap.containsKey(username)){
            System.out.println("User with this nickname already exists. TryAgain");
        } else {
            usersMap.put(username, password);
            System.out.println("Registered!");
        }
    }
    
     void logInUser(Map <String, Account> account){
        System.out.println("Please, enter your username: ");
        username = scanner.nextLine();
        System.out.println("Now, enter your password: ");
        password = scanner.nextLine();
        if(!usersMap.containsKey(username)){
            System.out.println("There`s no user with this nickname.");
        } else {
            if(usersMap.get(username).equals(password)){
                System.out.println("Login successful! Welcome " + username);
                System.out.println();
            } else {
                System.out.println("Password`s incorrect.");
            }
        }
    }

    public void getUsers(){
          if(usersMap.isEmpty()){
              System.out.println("There`re no users.");
          } else {
              System.out.println("All the library users: ");
              for (String user : usersMap.keySet()){
                  System.out.println("- " + user);
              }
              System.out.println();
          }
    }
}
