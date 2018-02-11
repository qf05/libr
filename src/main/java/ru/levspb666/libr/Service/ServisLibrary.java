package ru.levspb666.libr.Service;

import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import ru.levspb666.libr.Book;

import java.util.List;

public interface ServisLibrary {
     void addBook(Book book);
     void updateBook(RowEditEvent event);
     void removeBook(Book book);
     void readBook(Book book);
}
