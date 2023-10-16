package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.exceptions.DataProcessingException;
import com.example.onlinebookstore.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final EntityManagerFactory manager;

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
            throw new DataProcessingException("Can't save this book: " + book);
        } finally {
            entityManager.close();
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = manager.createEntityManager()) {
            return entityManager.createQuery("FROM Book ", Book.class).getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get all books from db ");
        }
    }
}
