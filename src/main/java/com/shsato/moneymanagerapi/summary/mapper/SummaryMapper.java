package com.shsato.moneymanagerapi.summary.mapper;

import com.shsato.moneymanagerapi.summary.dto.MonthlySummaryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface SummaryMapper {

    // 指定期間の固定費合計・変動費合計を取得する。
    MonthlySummaryResponse findMonthlySummary(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}