package com.shsato.moneymanagerapi.summary.controller;

import com.shsato.moneymanagerapi.summary.dto.MonthlySummaryResponse;
import com.shsato.moneymanagerapi.summary.service.SummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summary")
// 支出集計情報を取得するAPIを提供するController。
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    // ログインユーザーの指定年月の固定費合計・変動費合計を取得する。
    @GetMapping("/monthly")
    public MonthlySummaryResponse getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return summaryService.getMonthlySummary(userId, year, month);
    }
}