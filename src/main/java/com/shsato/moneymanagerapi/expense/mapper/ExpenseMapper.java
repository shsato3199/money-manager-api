package com.shsato.moneymanagerapi.expense.mapper;

import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExpenseMapper {

    // 変動費一覧取得
    List<ExpenseResponse> findVariableExpenses(
            @Param("userId") Long userId
    );
}