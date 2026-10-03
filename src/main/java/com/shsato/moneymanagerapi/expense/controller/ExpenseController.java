package com.shsato.moneymanagerapi.expense.controller;

import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import com.shsato.moneymanagerapi.expense.service.ExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
// 支出情報（固定費・変動費）を取得・登録・更新・削除するAPIを提供するController。
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ログインユーザーの変動費支出一覧を取得する。
    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;
        return expenseService.getVariableExpenses(userId);
    }
}