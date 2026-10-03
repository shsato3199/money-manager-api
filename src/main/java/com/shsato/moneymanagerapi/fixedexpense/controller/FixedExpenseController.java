package com.shsato.moneymanagerapi.fixedexpense.controller;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseResponse;
import com.shsato.moneymanagerapi.fixedexpense.service.FixedExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fixed-expenses")
// 固定費情報を取得・登録・更新・削除するAPIを提供するController。
public class FixedExpenseController {

    private final FixedExpenseService fixedExpenseService;

    public FixedExpenseController(FixedExpenseService fixedExpenseService) {
        this.fixedExpenseService = fixedExpenseService;
    }

    // ログインユーザーの固定費支出一覧を取得する。
    @GetMapping
    public List<FixedExpenseResponse> getFixedExpenses() {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return fixedExpenseService.getFixedExpenses(userId);
    }
}