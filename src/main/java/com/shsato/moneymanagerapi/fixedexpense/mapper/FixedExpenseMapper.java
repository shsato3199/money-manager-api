package com.shsato.moneymanagerapi.fixedexpense.mapper;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseResponse;
import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseRequest;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FixedExpenseMapper {

    // 指定期間の固定費一覧取得。
    List<FixedExpenseResponse> findFixedExpenses(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 固定費設定を登録する。
    void insertFixedExpense(
            @Param("userId") Long userId,
            @Param("request") FixedExpenseRequest request
    );
}