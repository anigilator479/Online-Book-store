package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.BookRequestDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.repository.BookRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto save(BookRequestDto bookRequestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(bookRequestDto)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by this id: " + id)));
    }
}
