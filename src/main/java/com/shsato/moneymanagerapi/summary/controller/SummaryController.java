package com.shsato.moneymanagerapi.summary.controller;

import com.shsato.moneymanagerapi.summary.dto.CategorySummaryResponse;
import com.shsato.moneymanagerapi.summary.dto.MonthlySummaryResponse;
import com.shsato.moneymanagerapi.summary.dto.PaymentSummaryResponse;
import com.shsato.moneymanagerapi.summary.service.SummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/summary")
// 支出集計情報を取得するAPIを提供するController。
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    // ログインユーザーの指定年月の固定費合計・変動費合計・総支出を取得する。
    @GetMapping("/monthly")
    public MonthlySummaryResponse getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return summaryService.getMonthlySummary(userId, year, month);
    }

    // ログインユーザーの指定年月のカテゴリ別支出集計を取得する。
    @GetMapping("/categories")
    public List<CategorySummaryResponse> getCategorySummary(
            @RequestParam int year,
            @RequestParam int month) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return summaryService.getCategorySummary(userId, year, month);
    }

    // ログインユーザーの指定年月の支払元別支出集計を取得する。
    @GetMapping("/payment-methods")
    public List<PaymentSummaryResponse> getPaymentSummary(
            @RequestParam int year,
            @RequestParam int month) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return summaryService.getPaymentSummary(userId, year, month);
    }
}