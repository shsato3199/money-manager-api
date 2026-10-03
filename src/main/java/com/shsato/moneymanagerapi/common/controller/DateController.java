package com.shsato.moneymanagerapi.common.controller;

import com.shsato.moneymanagerapi.common.dto.CurrentDateResponse;
import com.shsato.moneymanagerapi.common.service.DateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current-date")
// 現在日付を取得するAPIを提供するController。
public class DateController {

    private final DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    // 日本時間を基準とした現在日付を取得する。
    @GetMapping
    public CurrentDateResponse getCurrentDate() {
        return dateService.getCurrentDate();
    }
}