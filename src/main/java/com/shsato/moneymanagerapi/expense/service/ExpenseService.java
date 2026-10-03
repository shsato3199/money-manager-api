package com.shsato.moneymanagerapi.expense.service;

import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import com.shsato.moneymanagerapi.expense.mapper.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseMapper expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    // 指定年月の変動費一覧取得
    public List<ExpenseResponse> getVariableExpenses(
            Long userId,
            int year,
            int month) {

        // 指定年月の月初日を取得する。
        LocalDate startDate = LocalDate.of(year, month, 1);

        // 翌月の月初日を取得する。
        LocalDate endDate = startDate.plusMonths(1);

        return expenseMapper.findVariableExpenses(
                userId,
                startDate,
                endDate
        );
    }
}