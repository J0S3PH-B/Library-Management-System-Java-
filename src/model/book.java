package src.model;

public class book {
    private int id;
    private String title;
    private String author;
    private String category;
    private boolean status;

    public book () {} //empty constructor for book object

    public book (int id, String title, String author, String category, boolean status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
    }

    public book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = false;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%d] %s — %s (%s) [%s]", id, title, author, category, status);
    }
}
