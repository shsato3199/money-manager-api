package com.shsato.moneymanagerapi.fixedexpense.dto;

public record FixedExpenseRequest(
        String fixedExpenseName,
        Integer amount,
        Long categoryId,
        Long paymentMethodId,
        Integer paymentDay,
        String startYearMonth,
        String endYearMonth,
        Boolean autoGenerate,
        String memo
) {
}