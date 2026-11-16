package com.shsato.moneymanagerapi.category.repository;

import com.shsato.moneymanagerapi.category.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository
        extends JpaRepository<ExpenseCategory, Long> {

    // 指定ユーザーの有効なカテゴリ一覧を取得する。
    List<ExpenseCategory> findByUserIdAndIsActiveTrueOrderByDisplayOrderAscCategoryIdAsc(
            Long userId
    );

    // 同じユーザーに同名の有効カテゴリが存在するか確認する。
    boolean existsByUserIdAndCategoryNameAndIsActiveTrue(
            Long userId,
            String categoryName
    );

    // 指定ユーザーの有効なカテゴリをIDで取得する。
    Optional<ExpenseCategory> findByCategoryIdAndUserIdAndIsActiveTrue(
            Long categoryId,
            Long userId
    );

    // 更新対象自身を除いて、同名の有効なカテゴリが存在するか確認する。
    boolean existsByUserIdAndCategoryNameAndIsActiveTrueAndCategoryIdNot(
            Long userId,
            String categoryName,
            Long categoryId
    );

    // 指定ユーザーの有効なカテゴリの最大表示順を取得する。
    @Query("""
        SELECT COALESCE(MAX(c.displayOrder), 0)
        FROM ExpenseCategory c
        WHERE c.userId = :userId
          AND c.isActive = true
        """)
    Integer findMaxDisplayOrder(@Param("userId") Long userId);
}