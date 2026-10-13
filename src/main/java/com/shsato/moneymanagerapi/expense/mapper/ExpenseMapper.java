package com.shsato.moneymanagerapi.expense.mapper;

import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ExpenseMapper {

    // 指定期間の変動費一覧取得
    List<ExpenseResponse> findVariableExpenses(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    // 指定した変動費を更新する。
    int updateVariableExpense(
            @Param("userId") Long userId,
            @Param("id") Long id,
            @Param("transactionDate") LocalDate transactionDate,
            @Param("categoryId") Long categoryId,
            @Param("paymentMethodId") Long paymentMethodId,
            @Param("amount") BigDecimal amount,
            @Param("name") String name,
            @Param("shopName") String shopName,
            @Param("memo") String memo
    );
    // 指定した変動費を物理削除する。
    int deleteVariableExpense(
            @Param("userId") Long userId,
            @Param("id") Long id
    );
}