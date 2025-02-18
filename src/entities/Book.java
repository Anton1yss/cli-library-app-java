package entities;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private static final long serialVersionUID = 1;
    private String title;
    private String authorName;
    private String genre;
    private Integer publishingYear;
    private Boolean finishedReading;

    public Book(String title, String authorName, String genre, int publishingYear, boolean finishedReading) {
        this.title = title;
        this.authorName = authorName;
        this.genre = genre;
        this.publishingYear = publishingYear;
        this.finishedReading = finishedReading;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return publishingYear;
    }

    public boolean isFinishedReading() {
        return finishedReading;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.publishingYear = year;
    }

    public void setFinishedReading(boolean finishedReading) {
        this.finishedReading = finishedReading;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genre='" + genre + '\'' +
                ", publishingYear=" + publishingYear +
                ", finishedReading=" + finishedReading +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(title, book.title) && Objects.equals(authorName, book.authorName) && Objects.equals(genre, book.genre) && Objects.equals(publishingYear, book.publishingYear) && Objects.equals(finishedReading, book.finishedReading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authorName, genre, publishingYear, finishedReading);
    }
}
