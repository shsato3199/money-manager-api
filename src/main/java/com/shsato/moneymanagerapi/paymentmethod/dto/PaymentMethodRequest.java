package com.shsato.moneymanagerapi.paymentmethod.dto;

// 支払元登録・更新リクエスト。
public record PaymentMethodRequest(
        String name,
        String paymentType,
        Integer displayOrder
) {
}