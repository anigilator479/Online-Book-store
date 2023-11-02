package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import com.example.onlinebookstore.service.BookService;
import com.example.onlinebookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categories management", description = "Endpoints for managing books categories")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
public class CategoriesController {
    private final BookService bookService;
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Get a list of all categories")
    @GetMapping
    public List<CategoryResponseDto> getAll(
            @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @Operation(summary = "Get a category by id", description = "Get a specific category by id")
    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable @Positive Long id) {
        return categoryService.getById(id);
    }

    @Operation(summary = "Get a books list by category id",
            description = "Get a specific books list by category id")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable @Positive Long id) {
        return bookService.findAllByCategoriesId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete category by id", description = "Deletes a category by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create category", description = "Creates a new category in db")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponseDto createCategory(
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update category info by id",
            description = "Updates data about category in the db by id")
    @PutMapping("/{id}")
    public CategoryResponseDto updateBook(@RequestBody @Valid CategoryRequestDto requestDto,
                                              @PathVariable @Positive Long id) {
        return categoryService.updateById(id, requestDto);
    }
}
