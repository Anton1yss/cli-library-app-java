package entities;

import java.util.*;

public class User {
    private final String username;
    private final String password;
    private LinkedList<Book> books = new LinkedList<>();

    public User(String username, String password) {
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }
}
