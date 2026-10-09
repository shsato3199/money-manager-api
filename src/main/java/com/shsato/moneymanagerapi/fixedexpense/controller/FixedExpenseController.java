package com.shsato.moneymanagerapi.fixedexpense.controller;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseRequest;
import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseResponse;
import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseTemplateResponse;
import com.shsato.moneymanagerapi.fixedexpense.service.FixedExpenseService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixed-expenses")
// 固定費情報を取得・登録・更新・削除するAPIを提供するController。
public class FixedExpenseController {

    private final FixedExpenseService fixedExpenseService;

    public FixedExpenseController(FixedExpenseService fixedExpenseService) {
        this.fixedExpenseService = fixedExpenseService;
    }

    // ログインユーザーの指定年月の固定費支出一覧を取得する。
    @GetMapping
    public List<FixedExpenseResponse> getFixedExpenses(
            @RequestParam int year,
            @RequestParam int month) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return fixedExpenseService.getFixedExpenses(userId, year, month);
    }
    // 固定費設定一覧取得。
    @GetMapping("/templates")
    public List<FixedExpenseTemplateResponse> getFixedExpenseTemplates() {

        Long userId = 1L;

        return fixedExpenseService.getFixedExpenseTemplates(userId);
    }
    // ログインユーザーの固定費設定を登録する。
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFixedExpense(
            @RequestBody FixedExpenseRequest request) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        fixedExpenseService.createFixedExpense(userId, request);
    }
}