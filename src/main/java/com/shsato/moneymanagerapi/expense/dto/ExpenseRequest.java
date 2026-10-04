package com.shsato.moneymanagerapi.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

// 変動費登録・更新APIのリクエスト。
public class ExpenseRequest {

    private LocalDate transactionDate;
    private String name;
    private Long categoryId;
    private Long paymentMethodId;
    private BigDecimal amount;
    private String shopName;
    private String memo;

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}