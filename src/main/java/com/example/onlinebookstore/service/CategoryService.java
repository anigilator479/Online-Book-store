package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.category.CategoryRequestDto;
import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto updateById(Long id, CategoryRequestDto categoryRequestDto);

    void deleteById(Long id);
}
