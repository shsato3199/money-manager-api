package com.shsato.moneymanagerapi.summary.dto;

import java.math.BigDecimal;

// 支払元別月間支出集計APIのレスポンス。
public class PaymentSummaryResponse {

    private Long paymentMethodId;
    private String name;
    private BigDecimal amount;

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}