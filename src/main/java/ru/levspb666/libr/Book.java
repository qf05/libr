package ru.levspb666.libr;

import org.springframework.stereotype.Component;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Component
public class Book {
    private int id;
    private String title;
    private String discription;
    private String author;
    private String isbn;
    private int printYear;
    private byte readAlready;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrintYear() {
        return printYear;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public byte getReadAlready() {
        return readAlready;
    }

    public void setReadAlready(byte readAlready) {
        this.readAlready = readAlready;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book test = (Book) o;

        if (id != test.id) return false;
        if (printYear != test.printYear) return false;
        if (readAlready != test.readAlready) return false;
        if (title != null ? !title.equals(test.title) : test.title != null) return false;
        if (discription != null ? !discription.equals(test.discription) : test.discription != null) return false;
        if (author != null ? !author.equals(test.author) : test.author != null) return false;
        if (isbn != null ? !isbn.equals(test.isbn) : test.isbn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (discription != null ? discription.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + printYear;
        result = 31 * result + (int) readAlready;
        return result;
    }

    public static void showMessage(String message, String detail){
        FacesMessage msg = new FacesMessage(message, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
