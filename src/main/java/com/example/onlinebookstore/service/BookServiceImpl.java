package com.example.onlinebookstore.service;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
