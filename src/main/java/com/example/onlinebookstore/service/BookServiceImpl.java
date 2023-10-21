package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.CreateBookRequestDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toBook(createBookRequestDto)));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find book by this id: " + id));
    }

    @Override
    public BookDto updateById(CreateBookRequestDto createBookRequestDto, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't update book by this id: " + id));
        bookMapper.updateBook(createBookRequestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
