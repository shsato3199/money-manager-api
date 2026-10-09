package com.shsato.moneymanagerapi.category.service;

import com.shsato.moneymanagerapi.category.dto.CategoryRequest;
import com.shsato.moneymanagerapi.category.dto.CategoryResponse;
import com.shsato.moneymanagerapi.category.entity.ExpenseCategory;
import com.shsato.moneymanagerapi.category.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // カテゴリ一覧取得
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories(Long userId) {

        return categoryRepository
                .findByUserIdAndIsActiveTrueOrderByDisplayOrderAscCategoryIdAsc(userId)
                .stream()
                .map(category -> new CategoryResponse(
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.getDisplayOrder()
                ))
                .toList();
    }

    // カテゴリ登録
    @Transactional
    public CategoryResponse createCategory(Long userId, CategoryRequest request) {

        // カテゴリ名の入力チェック
        if (request == null || request.name() == null
                || request.name().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "カテゴリ名を入力してください。"
            );
        }

        String categoryName = request.name().trim();

        // カテゴリ名の文字数チェック
        if (categoryName.length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "カテゴリ名は100文字以内で入力してください。"
            );
        }

        // カテゴリ名の重複チェック
        if (categoryRepository.existsByUserIdAndCategoryNameAndIsActiveTrue(
                userId,
                categoryName
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "同じ名前のカテゴリが既に登録されています。"
            );
        }

        // 表示順は最大値の次に設定する。
        int displayOrder = categoryRepository.findMaxDisplayOrder(userId) + 1;

        // カテゴリ登録
        ExpenseCategory category = new ExpenseCategory();

        category.setUserId(userId);
        category.setCategoryName(categoryName);
        category.setDisplayOrder(displayOrder);
        category.setIsActive(true);

        OffsetDateTime now = OffsetDateTime.now();
        category.setCreatedAt(now);
        category.setUpdatedAt(now);

        ExpenseCategory savedCategory = categoryRepository.save(category);

        return new CategoryResponse(
                savedCategory.getCategoryId(),
                savedCategory.getCategoryName(),
                savedCategory.getDisplayOrder()
        );
    }
}