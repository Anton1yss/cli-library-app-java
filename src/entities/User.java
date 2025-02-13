package entities;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private static final long serialVersionUID = 1;
//    private String userID;
    private final String username;
    private final String password;
    private final LinkedList<Book> books = new LinkedList<>();

    public User(String username, String password) {
//        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(books, user.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, books);
    }

//    public String getUserID() {
//        return userID;
//    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return  /*"userID='" + userID + '\'' +*/
                ", username='" + username + '\'' +
                '}';
    }
}
