package com.shsato.moneymanagerapi.fixedexpense.mapper;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseTemplateResponse;
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
}