package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.CreateBookRequestDto;
import com.example.onlinebookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody CreateBookRequestDto requestDto, @PathVariable Long id) {
        return bookService.updateById(requestDto, id);
    }
}
