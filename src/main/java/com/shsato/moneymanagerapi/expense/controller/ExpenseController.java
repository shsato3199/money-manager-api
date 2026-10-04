package com.shsato.moneymanagerapi.expense.controller;

import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import com.shsato.moneymanagerapi.expense.dto.ExpenseRequest;

import com.shsato.moneymanagerapi.expense.service.ExpenseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
// 変動費情報を取得・登録・更新・削除するAPIを提供するController。
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ログインユーザーの指定年月の変動費支出一覧を取得する。
    @GetMapping
    public List<ExpenseResponse> getExpenses(
            @RequestParam int year,
            @RequestParam int month) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;
        return expenseService.getVariableExpenses(userId, year, month);
    }

    // ログインユーザーの変動費を登録する。
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(
            @RequestBody ExpenseRequest request) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;
        expenseService.createVariableExpense(userId, request);
    }
}