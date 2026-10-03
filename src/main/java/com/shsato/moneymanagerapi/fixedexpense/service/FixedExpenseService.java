package com.shsato.moneymanagerapi.fixedexpense.service;

import com.shsato.moneymanagerapi.fixedexpense.dto.FixedExpenseResponse;
import com.shsato.moneymanagerapi.fixedexpense.mapper.FixedExpenseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixedExpenseService {

    private final FixedExpenseMapper fixedExpenseMapper;

    public FixedExpenseService(FixedExpenseMapper fixedExpenseMapper) {
        this.fixedExpenseMapper = fixedExpenseMapper;
    }

    // 固定費一覧取得
    public List<FixedExpenseResponse> getFixedExpenses(Long userId) {
        return fixedExpenseMapper.findFixedExpenses(userId);
    }
}