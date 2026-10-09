package com.shsato.moneymanagerapi.fixedexpense.dto;

// 固定費設定一覧取得APIのレスポンス。
public class FixedExpenseTemplateResponse {

    private Long id;
    private String fixedExpenseName;
    private Integer amount;
    private Long categoryId;
    private String categoryName;
    private Long paymentMethodId;
    private String paymentMethodName;
    private Integer paymentDay;
    private String startYearMonth;
    private String endYearMonth;
    private Boolean autoGenerate;
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFixedExpenseName() {
        return fixedExpenseName;
    }

    public void setFixedExpenseName(String fixedExpenseName) {
        this.fixedExpenseName = fixedExpenseName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Integer getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Integer paymentDay) {
        this.paymentDay = paymentDay;
    }

    public String getStartYearMonth() {
        return startYearMonth;
    }

    public void setStartYearMonth(String startYearMonth) {
        this.startYearMonth = startYearMonth;
    }

    public String getEndYearMonth() {
        return endYearMonth;
    }

    public void setEndYearMonth(String endYearMonth) {
        this.endYearMonth = endYearMonth;
    }

    public Boolean getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(Boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}