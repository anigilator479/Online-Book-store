package com.example.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookstore.dto.book.BookDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:database/categories/add-category.sql")
@Sql(scripts = {
        "classpath:database/books/delete-books.sql",
        "classpath:database/categories/delete-categories.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Add new book into db")
    void createBook_ValidRequestDto_Success() {
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto(
                "How to cook",
                "Senior Tomato",
                "123456789",
                BigDecimal.valueOf(99),
                "Very nice book",
                "image src",
                Set.of(1L)
        );

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("How to cook");
        expected.setAuthor("Senior Tomato");
        expected.setIsbn("123456789");
        expected.setPrice(BigDecimal.valueOf(99));
        expected.setDescription("Very nice book");
        expected.setCoverImage("image src");
        expected.setCategoriesIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);

        MvcResult result = mockMvc.perform(
                        post("/api/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @SneakyThrows
    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get book by valid id")
    @Sql(scripts = "classpath:database/books/add-book.sql")
    void getBookById_ValidId_Success() {
        Long bookId = 1L;

        BookDto expected = new BookDto();
        expected.setId(bookId);
        expected.setTitle("Clean Code");
        expected.setAuthor("Martin");
        expected.setIsbn("123123123");
        expected.setPrice(BigDecimal.valueOf(99));
        expected.setDescription("Great book");
        expected.setCoverImage("img_src");
        expected.setCategoriesIds(Set.of(1L));

        MvcResult result = mockMvc.perform(get("/api/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete book by valid id")
    @Sql(scripts = "classpath:database/books/add-book.sql")
    void deleteBookById_ValidId_Success() {
        long bookId = 1L;

        mockMvc.perform(delete("/api/books/" + bookId))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "user")
    @Test
    @DisplayName("Find all books")
    @Sql(scripts = "classpath:database/books/add-three-books.sql")
    void findAllBooks_Valid_Success() {
        BookDto book1 = new BookDto();
        book1.setId(1L);
        book1.setTitle("Clean Code");
        book1.setAuthor("Martin");
        book1.setIsbn("123123123");
        book1.setPrice(new BigDecimal("99"));
        book1.setDescription("Great book");
        book1.setCoverImage("img_src");
        book1.setCategoriesIds(Set.of(1L));

        BookDto book2 = new BookDto();
        book2.setId(2L);
        book2.setTitle("How to cook");
        book2.setAuthor("Grandma");
        book2.setIsbn("777777777");
        book2.setPrice(new BigDecimal("48"));
        book2.setDescription("Not bad book");
        book2.setCoverImage("img_src");
        book2.setCategoriesIds(Set.of(1L));

        BookDto book3 = new BookDto();
        book3.setId(3L);
        book3.setTitle("Ukrainian literature 8 grade");
        book3.setAuthor("Avramenko");
        book3.setIsbn("666666666");
        book3.setPrice(new BigDecimal("10"));
        book3.setDescription("Awful book");
        book3.setCoverImage("img_src");
        book3.setCategoriesIds(Set.of(1L));

        List<BookDto> expected = List.of(book1, book2, book3);

        MvcResult result = mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();

        List<BookDto> actual =
                objectMapper.readerForListOf(BookDto.class).readValue(resultAsString);

        assertIterableEquals(expected, actual);
    }

    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update book by valid id")
    @Sql(scripts = "classpath:database/books/add-book.sql")
    void updateBook_ValidRequestDto_Success() {
        long bookId = 1L;

        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto(
                "How to cook",
                "Senior Tomato",
                "123456789",
                BigDecimal.valueOf(99),
                "Very nice book",
                "image src",
                Set.of(1L)
        );

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("How to cook");
        expected.setAuthor("Senior Tomato");
        expected.setIsbn("123456789");
        expected.setPrice(BigDecimal.valueOf(99));
        expected.setDescription("Very nice book");
        expected.setCoverImage("image src");
        expected.setCategoriesIds(Set.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);

        MvcResult result = mockMvc.perform(
                        put("/api/books/" + bookId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }
}
