package com.shsato.moneymanagerapi.common.service;

import com.shsato.moneymanagerapi.common.dto.CurrentDateResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class DateService {

    // 日本時間を基準に現在日付を取得する。
    public CurrentDateResponse getCurrentDate() {
        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Tokyo"));

        return new CurrentDateResponse(
                currentDate.getYear(),
                currentDate.getMonthValue(),
                currentDate.getDayOfMonth()
        );
    }
}