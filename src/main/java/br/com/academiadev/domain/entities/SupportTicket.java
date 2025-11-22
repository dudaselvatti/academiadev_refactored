package br.com.academiadev.domain.entities;

public class SupportTicket {
    private static int idCounter = 1;
    private int id;
    private User author;
    private String title;
    private String message;
    private boolean resolved;

    public SupportTicket(User author, String title, String message) {
        this.id = idCounter++;
        this.author = author;
        this.title = title;
        this.message = message;
        this.resolved = false;
    }

    public void closeTicket() {
        this.resolved = true;
    }

    public int getId() { return id; }
    public User getAuthor() { return author; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isResolved() { return resolved; }
    
    @Override
    public String toString() {
        return "[Ticket #" + id + "] " + title + " (Por: " + author.getName() + ")";
    }
}