package com.shsato.moneymanagerapi.fixedexpense.mapper;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseTemplateResponse;
import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

// 固定費設定のDB操作。
@Mapper
public interface FixedExpenseTemplateMapper {

    // 指定ユーザーの固定費設定一覧を取得する。
    List<FixedExpenseTemplateResponse> findFixedExpenseTemplates(
            @Param("userId") Long userId
    );
    // 自動生成対象の固定費設定を取得する。
    List<FixedExpenseTemplateResponse> findAutoGenerateFixedExpenseTemplates(
            @Param("userId") Long userId,
            @Param("currentYearMonth") String currentYearMonth
    );
    // 指定ユーザーの有効な固定費設定を1件取得する。
    FixedExpenseTemplateResponse findActiveFixedExpenseTemplateById(
            @Param("userId") Long userId,
            @Param("id") Long id
    );
    // 固定費設定を更新する。
    int updateFixedExpenseTemplate(
            @Param("userId") Long userId,
            @Param("id") Long id,
            @Param("request") FixedExpenseRequest request
    );
    // 固定費設定を論理削除する。
    int deleteFixedExpenseTemplate(
            @Param("userId") Long userId,
            @Param("id") Long id
    );
}