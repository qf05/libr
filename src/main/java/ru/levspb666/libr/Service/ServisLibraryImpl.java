package ru.levspb666.libr.Service;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.levspb666.libr.Book;
import ru.levspb666.libr.Controller.Controller;
import ru.levspb666.libr.DAO.BookDao;

@Component
@Scope("singleton")
public class ServisLibraryImpl implements ServisLibrary {

    @Autowired
    private BookDao bookDAO;

    @Autowired
    private Controller controller;

    private String title;
    private String discription;
    private String author;
    private String isbn;
    private int printYear;
    private LazyDataModel<Book> lazyModel;

    public LazyDataModel<Book> getLazyModel() {
        if (lazyModel == null){
            lazyModel = bookDAO.getLazyDataModel();
        }
        return lazyModel;
    }

    public void setReadBook(Book book) {
       readBook(book);
    }
     public void setDelete(Book book) {
         removeBook(book);
    }

    @Override
    public void addBook(Book book) {

    }

    @Override
    public void removeBook(Book book) {
        controller.delete(book);
        controller.showMessage("Delete ", book.getTitle());
    }

    @Override
    public void readBook(Book book) {
        if (book.getReadAlready()==0) {
            controller.readBook(book);
            controller.showMessage("You read: ", book.getTitle());
        }else {
            controller.showMessage("AHTUNG! ","Вы это уже читали!!!");
        }
    }

    @Override
    public void updateBook(RowEditEvent event) {
        controller.update(event);
        controller.showMessage("Book Edited ", "Book Id: " + ((Book) event.getObject()).getId()+"");
    }

    public void onRowCancel(RowEditEvent event) {
        controller.showMessage("Edit Cancelled ", ((Book) event.getObject()).getId()+"");
    }

    public void addBook() {
        controller.addBook(title, discription, author, isbn, printYear);
        controller.showMessage("Add book: ",title);
        nulleter();
    }

    public void nulleter(){
        title = null;
        discription = null;
        author = null;
        isbn = null;
        printYear = 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscription() {
        return discription;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPrintYear() {
        return printYear;
    }
}