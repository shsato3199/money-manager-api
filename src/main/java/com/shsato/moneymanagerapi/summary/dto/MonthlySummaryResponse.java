package com.shsato.moneymanagerapi.summary.dto;

import java.math.BigDecimal;

// 月間支出集計APIのレスポンス。
public class MonthlySummaryResponse {

    private BigDecimal fixedExpenseTotal;
    private BigDecimal variableExpenseTotal;
    private BigDecimal totalExpense;

    public BigDecimal getFixedExpenseTotal() {
        return fixedExpenseTotal;
    }

    public void setFixedExpenseTotal(BigDecimal fixedExpenseTotal) {
        this.fixedExpenseTotal = fixedExpenseTotal;
    }

    public BigDecimal getVariableExpenseTotal() {
        return variableExpenseTotal;
    }

    public void setVariableExpenseTotal(BigDecimal variableExpenseTotal) {
        this.variableExpenseTotal = variableExpenseTotal;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }
}