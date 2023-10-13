package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractRepository implements BookRepository {

    protected BookRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;
        EntityManager entityManager = manager.createEntityManager();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save this book: " + book);
        } finally {
            entityManager.close();
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = manager.createEntityManager()) {
            TypedQuery<Book> query = entityManager.createQuery("FROM Book ", Book.class);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Can't save this book: ");
        }
    }
}
