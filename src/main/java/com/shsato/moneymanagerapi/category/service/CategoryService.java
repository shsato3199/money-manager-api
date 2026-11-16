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

    // カテゴリ更新
    @Transactional
    public CategoryResponse updateCategory(
            Long userId,
            Long categoryId,
            CategoryRequest request
    ) {

        // 更新対象のカテゴリを取得する。
        ExpenseCategory category = categoryRepository
                .findByCategoryIdAndUserIdAndIsActiveTrue(categoryId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "カテゴリが見つかりません。"
                ));

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

        // 自分自身を除いたカテゴリ名の重複チェック
        if (categoryRepository.existsByUserIdAndCategoryNameAndIsActiveTrueAndCategoryIdNot(
                userId,
                categoryName,
                categoryId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "同じ名前のカテゴリが既に登録されています。"
            );
        }

        // 有効なカテゴリ一覧を表示順で取得する。
        List<ExpenseCategory> categories = categoryRepository
                .findByUserIdAndIsActiveTrueOrderByDisplayOrderAscCategoryIdAsc(userId);

        // 現在の表示順を取得する。
        int currentDisplayOrder = -1;

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(categoryId)) {
                currentDisplayOrder = i + 1;
                break;
            }
        }

        if (currentDisplayOrder == -1) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "カテゴリが見つかりません。"
            );
        }

        // 表示順が指定されていない場合は現在の表示順を使用する。
        int newDisplayOrder = request.displayOrder() == null
                ? currentDisplayOrder
                : request.displayOrder();

        // 表示順の入力チェック
        if (newDisplayOrder < 1 || newDisplayOrder > categories.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "表示順が不正です。"
            );
        }

        // 更新対象のカテゴリを一覧から一度除外する。
        categories.removeIf(
                item -> item.getCategoryId().equals(categoryId)
        );

        // 指定された表示順の位置に挿入する。
        categories.add(newDisplayOrder - 1, category);

        OffsetDateTime now = OffsetDateTime.now();

        // すべてのカテゴリの表示順を振り直す。
        for (int i = 0; i < categories.size(); i++) {

            ExpenseCategory item = categories.get(i);

            int displayOrder = i + 1;

            // 表示順が変更された場合のみ更新する。
            if (!Integer.valueOf(displayOrder).equals(item.getDisplayOrder())) {
                item.setDisplayOrder(displayOrder);
                item.setUpdatedAt(now);
            }
        }

        // カテゴリ名と更新日時を変更する。
        category.setCategoryName(categoryName);
        category.setUpdatedAt(now);

        // カテゴリ一覧を更新する。
        categoryRepository.saveAll(categories);

        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDisplayOrder()
        );
    }

    // カテゴリ論理削除
    @Transactional
    public void deleteCategory(Long userId, Long categoryId) {

        // 削除対象のカテゴリを取得する。
        ExpenseCategory category = categoryRepository
                .findByCategoryIdAndUserIdAndIsActiveTrue(categoryId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "カテゴリが見つかりません。"
                ));

        // 有効なカテゴリ一覧を取得する。
        List<ExpenseCategory> categories = categoryRepository
                .findByUserIdAndIsActiveTrueOrderByDisplayOrderAscCategoryIdAsc(userId);

        // カテゴリは最低1件残す。
        if (categories.size() <= 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "最後のカテゴリは削除できません。"
            );
        }

        OffsetDateTime now = OffsetDateTime.now();

        // 論理削除する。
        category.setIsActive(false);
        category.setUpdatedAt(now);

        // 削除対象を一覧から除外する。
        categories.removeIf(
                item -> item.getCategoryId().equals(categoryId)
        );

        // 削除後の表示順を振り直す。
        for (int i = 0; i < categories.size(); i++) {

            ExpenseCategory item = categories.get(i);

            int displayOrder = i + 1;

            // 表示順が変更された場合のみ更新する。
            if (!Integer.valueOf(displayOrder).equals(item.getDisplayOrder())) {
                item.setDisplayOrder(displayOrder);
                item.setUpdatedAt(now);
            }
        }

        // 削除対象を更新する。
        categoryRepository.save(category);

        // 残ったカテゴリの表示順を更新する。
        categoryRepository.saveAll(categories);
    }
}