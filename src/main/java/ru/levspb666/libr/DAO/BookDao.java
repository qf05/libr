package ru.levspb666.libr.DAO;

import org.primefaces.model.LazyDataModel;
import ru.levspb666.libr.Book;

import java.util.List;
import java.util.Map;

public interface BookDao {

    List<Book> getBooks();
    void addBook (Book book);
    void updateBook (Book book);
    void removeBook (Book book);
    LazyDataModel<Book> getLazyDataModel();

}
