package com.shsato.moneymanagerapi.paymentmethod.dto;

// 支払元一覧取得レスポンス。
public record PaymentMethodResponse(
        Long id,
        String name,
        String paymentType,
        Integer displayOrder
) {
}