package com.shsato.moneymanagerapi.category.controller;

import com.shsato.moneymanagerapi.category.dto.CategoryRequest;
import com.shsato.moneymanagerapi.category.dto.CategoryResponse;
import com.shsato.moneymanagerapi.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // カテゴリ一覧取得
    @GetMapping
    public List<CategoryResponse> getCategories() {

        // Googleログイン実装までは仮のユーザーIDを使用する。
        Long userId = 1L;

        return categoryService.getCategories(userId);
    }

    // カテゴリ登録
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) {

        // Googleログイン実装までは仮のユーザーIDを使用する。
        Long userId = 1L;

        return categoryService.createCategory(userId, request);
    }
}