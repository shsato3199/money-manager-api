package com.shsato.moneymanagerapi.summary.service;

import com.shsato.moneymanagerapi.summary.dto.MonthlySummaryResponse;
import com.shsato.moneymanagerapi.summary.mapper.SummaryMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SummaryService {

    private final SummaryMapper summaryMapper;

    public SummaryService(SummaryMapper summaryMapper) {
        this.summaryMapper = summaryMapper;
    }

    // 指定年月の固定費合計・変動費合計を取得する。
    public MonthlySummaryResponse getMonthlySummary(
            Long userId,
            int year,
            int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return summaryMapper.findMonthlySummary(
                userId,
                startDate,
                endDate
        );
    }
}