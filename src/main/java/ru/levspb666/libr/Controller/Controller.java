package ru.levspb666.libr.Controller;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.levspb666.libr.Book;
import ru.levspb666.libr.DAO.BookDao;

@RestController
public class Controller {

    @Autowired
    BookDao bookDao;

    public void addBook(String title, String discription, String author, String isbn, int printYear){
        Book book = new Book();
        book.setTitle(title);
        book.setDiscription(discription);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrintYear(printYear);
        bookDao.addBook(book);
    }

    public void update(RowEditEvent event){
        Book book = (Book) event.getObject();
        book.setReadAlready((byte)0);
        bookDao.updateBook(book);

    }

    public void delete(Book book){
        bookDao.removeBook(book);

    }

    public void readBook(Book book){
       book.setReadAlready((byte)1);
        bookDao.updateBook(book);
    }

    public void showMessage(String message, String detail){
        Book.showMessage(message, detail);
    }
}
