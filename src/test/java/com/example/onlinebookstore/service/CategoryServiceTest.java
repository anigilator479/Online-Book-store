package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import com.example.onlinebookstore.exceptions.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CategoryMapper;
import com.example.onlinebookstore.mapper.impl.CategoryMapperImpl;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.CategoryRepository;
import com.example.onlinebookstore.service.impl.CategoryServiceImpl;
import java.util.Optional;
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
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    @DisplayName("Verify the correct category returns when category exists")
    public void getCategoryById_WithValidId_ShouldReturnValidCategory() {
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Adults");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryResponseDto actual = categoryService.getById(categoryId);

        CategoryResponseDto expected = new CategoryResponseDto(1L, "Adults", null);

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("Verify the correct exception message returns with not valid id")
    public void getCategoryById_WithNotValidId_ShouldReturnException() {
        Long categoryId = 1000L;

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(categoryId));

        String expected = "Can't find category by this categoryId: " + categoryId;

        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify the correct bookDto returns after book was saved")
    public void saveCategory_Valid_ShouldReturnCategoryResponseDto() {
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Adults");
        category.setDescription("For adults");

        CategoryRequestDto requestDto = new CategoryRequestDto("Adults", "For adults");

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryResponseDto actual = categoryService.save(requestDto);
        CategoryResponseDto expected = new CategoryResponseDto(categoryId, "Adults", "For adults");

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("Verify correct delete method invocation")
    public void deleteCategoryById_ValidId_Success() {
        Long id = 1L;

        Mockito.doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        Mockito.verify(categoryRepository).deleteById(id);
        Mockito.verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Verify correct work of updating method")
    public void updateCategoryById_ValidId_Success() {
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Child");
        category.setDescription("Description");

        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                "Adults",
                "Description"
        );

        CategoryRequestDto requestDto = new CategoryRequestDto(
                "Adults",
                "Description"
        );

        Mockito.when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(category))
                .thenReturn(category);

        CategoryResponseDto actual = categoryService.updateById(categoryId, requestDto);

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }
}
