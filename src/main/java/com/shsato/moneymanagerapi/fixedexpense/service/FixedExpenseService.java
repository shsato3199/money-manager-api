package com.shsato.moneymanagerapi.fixedexpense.service;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseRequest;
import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseResponse;
import com.shsato.moneymanagerapi.fixedexpense.mapper.FixedExpenseMapper;
import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseTemplateResponse;
import com.shsato.moneymanagerapi.fixedexpense.mapper.FixedExpenseTemplateMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class FixedExpenseService {

    private final FixedExpenseMapper fixedExpenseMapper;
    private final FixedExpenseTemplateMapper fixedExpenseTemplateMapper;
    public FixedExpenseService(
            FixedExpenseMapper fixedExpenseMapper,
            FixedExpenseTemplateMapper fixedExpenseTemplateMapper
    ) {
        this.fixedExpenseMapper = fixedExpenseMapper;
        this.fixedExpenseTemplateMapper = fixedExpenseTemplateMapper;
    }

    // 指定年月の固定費一覧取得。
    public List<FixedExpenseResponse> getFixedExpenses(
            Long userId,
            int year,
            int month) {

        // 指定年月の月初日を取得する。
        LocalDate startDate = LocalDate.of(year, month, 1);

        // 翌月の月初日を取得する。
        LocalDate endDate = startDate.plusMonths(1);

        return fixedExpenseMapper.findFixedExpenses(
                userId,
                startDate,
                endDate
        );
    }

    // 固定費設定一覧を取得する。
    public List<FixedExpenseTemplateResponse> getFixedExpenseTemplates(Long userId) {

        return fixedExpenseTemplateMapper.findFixedExpenseTemplates(userId);
    }

    // 固定費設定を登録する。
    @Transactional
    public void createFixedExpense(Long userId, FixedExpenseRequest request) {

        // 固定費名チェック。
        if (request.fixedExpenseName() == null
                || request.fixedExpenseName().isBlank()
                || request.fixedExpenseName().trim().length() > 100) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "固定費名が不正です。"
            );
        }

        // 金額チェック。
        if (request.amount() == null || request.amount() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "金額が不正です。"
            );
        }

        // カテゴリ・支払元チェック。
        if (request.categoryId() == null || request.paymentMethodId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "カテゴリまたは支払元が未指定です。"
            );
        }

        // 支払日チェック。
        if (request.paymentDay() == null
                || request.paymentDay() < 1
                || request.paymentDay() > 31) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "支払日が不正です。"
            );
        }

        // 自動生成フラグチェック。
        if (request.autoGenerate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "自動生成フラグが未指定です。"
            );
        }

        // メモの文字数チェック。
        if (request.memo() != null && request.memo().length() > 500) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "メモは500文字以内で入力してください。"
            );
        }

        // 開始年月・終了年月チェック。
        YearMonth startYearMonth;
        YearMonth endYearMonth = null;

        try {
            startYearMonth = YearMonth.parse(request.startYearMonth());

            if (request.endYearMonth() != null
                    && !request.endYearMonth().isBlank()) {

                endYearMonth = YearMonth.parse(request.endYearMonth());
            }
        } catch (DateTimeParseException | NullPointerException error) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "年月の形式が不正です。"
            );
        }

        if (endYearMonth != null && endYearMonth.isBefore(startYearMonth)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "終了年月は開始年月以降を指定してください。"
            );
        }

        // 固定費設定を登録する。
        fixedExpenseMapper.insertFixedExpense(userId, request);
    }
}