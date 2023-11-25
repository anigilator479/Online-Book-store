package com.example.onlinebookstore.service;

import static org.mockito.Mockito.times;

import com.example.onlinebookstore.dto.book.BookDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.mapper.impl.BookMapperImpl;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CategoryRepository;
import com.example.onlinebookstore.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Spy
    private BookMapper bookMapper = new BookMapperImpl();

    @Test
    @DisplayName("Verify the correct book returns when book exists")
    public void getBookById_WithValidId_ShouldReturnValidBook() {
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setAuthor("Senior Tomato");
        book.setTitle("How to cook");
        book.setIsbn("123456789");
        book.setPrice(BigDecimal.valueOf(99));
        book.setDescription("Very nice book");
        book.setCoverImage("image src");

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDto expected = new BookDto();
        expected.setTitle(book.getTitle());
        expected.setAuthor(book.getAuthor());
        expected.setIsbn(book.getIsbn());
        expected.setPrice(book.getPrice());
        expected.setDescription(book.getDescription());
        expected.setCoverImage(book.getCoverImage());
        expected.setCategoriesIds(new HashSet<>());

        BookDto actual = bookService.getById(bookId);

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @DisplayName("Verify the correct exception message returns with not valid id")
    public void getBookById_WithNotValidId_ShouldReturnException() {
        Long bookId = 1000L;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.getById(bookId));

        String expected = "Can't find book by this bookId: " + bookId;

        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify the correct bookDto returns after book was saved")
    public void saveBook_Valid_ShouldReturnBookDto() {
        Long categoryId = 1L;
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto(
                "How to cook",
                "Senior Tomato",
                "123456789",
                BigDecimal.valueOf(99),
                "Very nice book",
                "image src",
                Set.of(categoryId)
        );

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Adults");

        Book book = new Book();
        book.setTitle(bookRequestDto.title());
        book.setAuthor(bookRequestDto.author());
        book.setIsbn(bookRequestDto.isbn());
        book.setPrice(bookRequestDto.price());
        book.setDescription(bookRequestDto.description());
        book.setCoverImage(bookRequestDto.coverImage());
        book.setCategories(Set.of(category));

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookDto expected = new BookDto();
        expected.setTitle(bookRequestDto.title());
        expected.setAuthor(bookRequestDto.author());
        expected.setIsbn(bookRequestDto.isbn());
        expected.setPrice(bookRequestDto.price());
        expected.setDescription(bookRequestDto.description());
        expected.setCoverImage(bookRequestDto.coverImage());
        expected.setCategoriesIds(Set.of(category.getId()));

        BookDto actual = bookService.save(bookRequestDto);

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @DisplayName("Verify the correct exception message returns with not valid category id")
    public void saveBook_NotValidCategoryId_ShouldReturnException() {
        Long categoryId = -1L;
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto(
                "How to cook",
                "Senior Tomato",
                "123456789",
                BigDecimal.valueOf(99),
                "Very nice book",
                "image src",
                Set.of(categoryId)
        );

        Mockito.when(categoryRepository.findById(categoryId))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.save(bookRequestDto));
    }

    @Test
    @DisplayName("Verify correct delete method invocation")
    public void deleteBookById_ValidId_Success() {
        Long id = 1L;

        Mockito.doNothing().when(bookRepository).deleteById(id);

        bookService.deleteById(id);

        Mockito.verify(bookRepository, times(1)).deleteById(id);
        Mockito.verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Verify correct work of updating method")
    public void updateBookById_ValidId_Success() {
        Long bookId = 1L;
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Adults");

        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto(
                "How to cook",
                "Senior Tomato",
                "123456789",
                BigDecimal.valueOf(99),
                "Very nice book",
                "image src",
                Set.of(categoryId)
        );

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Mega super puper");
        book.setAuthor(bookRequestDto.author());
        book.setIsbn(bookRequestDto.isbn());
        book.setPrice(BigDecimal.valueOf(200));
        book.setDescription(bookRequestDto.description());
        book.setCoverImage(bookRequestDto.coverImage());
        book.setCategories(Set.of(category));

        BookDto expected = new BookDto();
        expected.setId(bookId);
        expected.setTitle(bookRequestDto.title());
        expected.setAuthor(bookRequestDto.author());
        expected.setIsbn(bookRequestDto.isbn());
        expected.setPrice(bookRequestDto.price());
        expected.setDescription(bookRequestDto.description());
        expected.setCoverImage(bookRequestDto.coverImage());
        expected.setCategoriesIds(Set.of(category.getId()));

        Mockito.when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book))
                .thenReturn(book);

        BookDto actual = bookService.updateById(bookRequestDto, bookId);

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }
}
