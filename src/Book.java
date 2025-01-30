public class Book {
    String title , authorName , genre;
    int publishingYear;
    boolean finishedReading;

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
}
