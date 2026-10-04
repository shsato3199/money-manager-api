package com.shsato.moneymanagerapi.summary.dto;

import java.math.BigDecimal;

// カテゴリ別月間支出集計APIのレスポンス。
public class CategorySummaryResponse {

    private Long categoryId;
    private String name;
    private BigDecimal amount;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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