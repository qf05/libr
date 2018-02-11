package ru.levspb666.libr.DAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.levspb666.libr.Book;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;

    private List<Book> books;

    @Transactional
    @Override
    public List<Book> getBooks() {
        if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
            sessionFactory.getCurrentSession().beginTransaction();
        }
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Book.class);
        books = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return books;

    }

    private LazyDataModel<Book> lazyDataModel = new LazyDataModel<Book>() {
        @Override
        @Transactional
        public List<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
            sessionFactory.getCurrentSession().beginTransaction();
            }
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(Book.class);
            books = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            List<Book> lazyBook = new ArrayList<>();
            //filter
            for(Book book : books) {
                boolean match = true;
                if (filters != null) {
                    for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                        try {
                            String filterProperty = it.next();
                            Object filterValue = filters.get(filterProperty);
                            Field privateField = book.getClass().getDeclaredField(filterProperty);
                            privateField.setAccessible(true);
                            String fieldValue = String.valueOf(privateField.get(book));
                            if(filterValue == null || fieldValue.trim().equals("") ||
                                    fieldValue.trim().toLowerCase().contains(filterValue.toString().trim().toLowerCase())) {
                                match = true;
                            }
                            else {
                                match = false;
                                break;
                            }
                        } catch(Exception e) {
                            match = false;
                        }
                    }
                }
                if(match) {
                    lazyBook.add(book);
                }
            }

            sessionFactory.getCurrentSession().close();
            //rowCount
            int dataSize = lazyBook.size();
            this.setRowCount(dataSize);

            //paginate
            if(dataSize > pageSize) {
                try {
                    return lazyBook.subList(first, first + pageSize);
                }
                catch(IndexOutOfBoundsException e) {
                    return lazyBook.subList(first, first + (dataSize % pageSize));
                }
            }
            else {
                return lazyBook;
            }
        }
    };

    @Transactional
    @Override
    public void addBook(Book book) {
        if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
            sessionFactory.getCurrentSession().beginTransaction();
        }
        Session session = this.sessionFactory.getCurrentSession();
        session.save(book);
        session.getTransaction().commit();
        session.close();
        sessionFactory.getCurrentSession().close();
    }

    @Transactional
    @Override
    public void updateBook(Book book) {
        if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
            sessionFactory.getCurrentSession().beginTransaction();
        }
        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);
        session.getTransaction().commit();
        session.close();
        sessionFactory.getCurrentSession().close();
    }

    @Transactional
    @Override
    public void removeBook(Book book) {

        if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
            sessionFactory.getCurrentSession().beginTransaction();
        }
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(book);
        session.getTransaction().commit();
        session.close();
        sessionFactory.getCurrentSession().close();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public LazyDataModel<Book> getLazyDataModel() {
        return lazyDataModel;
    }
}
