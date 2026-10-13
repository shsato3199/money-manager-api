package com.shsato.moneymanagerapi.paymentmethod.dto;

// 支払元登録リクエスト。
public record PaymentMethodRequest(
        String name,
        String paymentType
) {
}