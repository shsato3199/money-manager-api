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

    // 固定費設定を登録し、採番された設定IDを返す。
    Long insertFixedExpense(
            @Param("userId") Long userId,
            @Param("request") FixedExpenseRequest request
    );

    // 固定費設定をもとに指定日の固定費支出を登録する。
    void insertFixedExpenseTransaction(
            @Param("userId") Long userId,
            @Param("fixedExpenseTemplateId") Long fixedExpenseTemplateId,
            @Param("transactionDate") LocalDate transactionDate
    );

    // 更新後の対象期間外となる生成済み固定費明細を削除する。
    int deleteFixedExpenseTransactionsOutsideRange(
            @Param("userId") Long userId,
            @Param("fixedExpenseTemplateId") Long fixedExpenseTemplateId,
            @Param("startYearMonth") String startYearMonth,
            @Param("endYearMonth") String endYearMonth
    );

    // 当月以降の生成済み固定費明細を更新する。
    int updateFixedExpenseTransactionsFromMonth(
            @Param("userId") Long userId,
            @Param("fixedExpenseTemplateId") Long fixedExpenseTemplateId,
            @Param("currentMonthStart") LocalDate currentMonthStart,
            @Param("request") FixedExpenseRequest request
    );
}