package com.shsato.moneymanagerapi.fixedexpense.service;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseResponse;
import com.shsato.moneymanagerapi.fixedexpense.mapper.FixedExpenseMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FixedExpenseService {

    private final FixedExpenseMapper fixedExpenseMapper;

    public FixedExpenseService(FixedExpenseMapper fixedExpenseMapper) {
        this.fixedExpenseMapper = fixedExpenseMapper;
    }

    // 指定年月の固定費一覧取得
    public List<FixedExpenseResponse> getFixedExpenses(
            Long userId,
            int year,
            int month) {

        // 指定年月の月初日を取得する。
        LocalDate startDate = LocalDate.of(year, month, 1);

        // 翌月の月初日を取得する。
        LocalDate endDate = startDate.plusMonths(1);

        return fixedExpenseMapper.findFixedExpenses(
                userId,
                startDate,
                endDate
        );
    }
}