package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.BookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(BookRequestDto book);

    List<BookDto> findAll();

    BookDto getById(Long id);
}
