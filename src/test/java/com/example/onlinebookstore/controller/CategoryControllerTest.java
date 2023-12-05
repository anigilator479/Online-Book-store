package com.example.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
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
@Sql(scripts = "classpath:database/categories/delete-categories.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class CategoryControllerTest {
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
    @DisplayName("Add new category into db")
    void createCategory_ValidRequestDto_Success() {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "Adults",
                "only 18+"
        );

        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                "Adults",
                "only 18+"
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto actual =
                objectMapper.readValue(result.getResponse()
                        .getContentAsString(), CategoryResponseDto.class);

        assertNotNull(actual);
        assertNotNull(actual.id());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual,
                "id"));
    }

    @SneakyThrows
    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get Category by valid id")
    @Sql(scripts = "classpath:database/categories/add-category.sql")
    void getCategoryById_ValidId_Success() {
        long categoryId = 1L;

        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                "Adults",
                "Aboba 10 IQ"
        );

        MvcResult result = mockMvc.perform(get("/api/categories/" + categoryId))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), CategoryResponseDto.class);

        assertNotNull(actual);
        assertNotNull(actual.id());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete category by valid id")
    @Sql(scripts = "classpath:database/categories/add-category.sql")
    void deleteCategoryById_ValidId_Success() {
        long categoryId = 1L;

        mockMvc.perform(delete("/api/categories/" + categoryId))
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @WithMockUser(username = "user")
    @Test
    @DisplayName("Find all categories")
    @Sql(scripts = "classpath:database/categories/add-three-categories.sql")
    void findAllCategories_Valid_Success() {

        CategoryResponseDto category1 = new CategoryResponseDto(
                1L,
                "Adults",
                "Aboba 10 IQ"
        );

        CategoryResponseDto category2 = new CategoryResponseDto(
                2L,
                "children",
                "Aboba 5 IQ"
        );

        CategoryResponseDto category3 = new CategoryResponseDto(
                3L,
                "third category",
                "Aboba 1 IQ"
        );

        List<CategoryResponseDto> expected = List.of(category1, category2, category3);

        MvcResult result = mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();

        List<CategoryResponseDto> actual =
                objectMapper.readerForListOf(CategoryResponseDto.class)
                        .readValue(resultAsString);

        assertIterableEquals(expected, actual);
    }

    @SneakyThrows
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update category by valid id")
    @Sql(scripts = "classpath:database/categories/add-category.sql")
    void updateCategory_ValidRequestDto_Success() {
        long categoryId = 1L;

        CategoryRequestDto requestDto = new CategoryRequestDto(
                "children",
                "Aboba 5 IQ"
        );

        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                "children",
                "Aboba 5 IQ"
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        put("/api/categories/" + categoryId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual =
                objectMapper.readValue(result.getResponse()
                        .getContentAsString(), CategoryResponseDto.class);

        assertNotNull(actual);
        assertNotNull(actual.id());
        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @SneakyThrows
    @WithMockUser(username = "user")
    @Test
    @DisplayName("Find all books by category id")
    @Sql(scripts = {"classpath:database/books/add-three-books.sql",
            "classpath:database/categories/add-category.sql"})
    @Sql(scripts = "classpath:database/books/delete-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findBooksByCategoryId_ValidId_Success() {

        long categoryId = 1L;

        BookDtoWithoutCategoryIds book1 = new BookDtoWithoutCategoryIds(
                1L,
                "Clean Code",
                "Martin",
                "123123123",
                BigDecimal.valueOf(99),
                "Great book",
                "img_src"
        );

        BookDtoWithoutCategoryIds book2 = new BookDtoWithoutCategoryIds(
                2L,
                "How to cook",
                "Grandma",
                "777777777",
                BigDecimal.valueOf(48),
                "Not bad book",
                "img_src"
        );

        BookDtoWithoutCategoryIds book3 = new BookDtoWithoutCategoryIds(
                3L,
                "Ukrainian literature 8 grade",
                "Avramenko",
                "666666666",
                BigDecimal.valueOf(10),
                "Awful book",
                "img_src"
        );

        List<BookDtoWithoutCategoryIds> expected = List.of(book1, book2, book3);

        MvcResult result = mockMvc.perform(get(String.format("/api/categories/%d/books",
                        categoryId)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        System.out.println(resultAsString);

        List<BookDtoWithoutCategoryIds> actual =
                objectMapper.readerForListOf(BookDtoWithoutCategoryIds.class)
                        .readValue(resultAsString);

        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected.get(i), actual.get(i)));
        }
    }
}
