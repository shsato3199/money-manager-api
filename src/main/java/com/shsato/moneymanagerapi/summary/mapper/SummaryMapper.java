package com.shsato.moneymanagerapi.summary.mapper;

import com.shsato.moneymanagerapi.summary.dto.CategorySummaryResponse;
import com.shsato.moneymanagerapi.summary.dto.MonthlySummaryResponse;
import com.shsato.moneymanagerapi.summary.dto.PaymentSummaryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SummaryMapper {

    // 指定期間の固定費合計・変動費合計・総支出を取得する。
    MonthlySummaryResponse findMonthlySummary(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 指定期間のカテゴリ別支出集計を取得する。
    List<CategorySummaryResponse> findCategorySummary(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 指定期間の支払元別支出集計を取得する。
    List<PaymentSummaryResponse> findPaymentSummary(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}