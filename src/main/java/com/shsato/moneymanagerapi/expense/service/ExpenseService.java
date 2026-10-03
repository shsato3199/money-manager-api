package com.shsato.moneymanagerapi.expense.service;

import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import com.shsato.moneymanagerapi.expense.mapper.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseMapper expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    // 変動費一覧取得
    public List<ExpenseResponse> getVariableExpenses(Long userId) {
        return expenseMapper.findVariableExpenses(userId);
    }
}