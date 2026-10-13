package com.shsato.moneymanagerapi.expense.service;

import com.shsato.moneymanagerapi.expense.dto.ExpenseRequest;
import com.shsato.moneymanagerapi.expense.dto.ExpenseResponse;
import com.shsato.moneymanagerapi.expense.entity.ExpenseTransaction;
import com.shsato.moneymanagerapi.expense.mapper.ExpenseMapper;
import com.shsato.moneymanagerapi.expense.repository.ExpenseTransactionRepository;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseMapper expenseMapper;
    private final ExpenseTransactionRepository expenseTransactionRepository;

    public ExpenseService(
            ExpenseMapper expenseMapper,
            ExpenseTransactionRepository expenseTransactionRepository) {
        this.expenseMapper = expenseMapper;
        this.expenseTransactionRepository = expenseTransactionRepository;
    }

    // 指定年月の変動費一覧取得
    public List<ExpenseResponse> getVariableExpenses(
            Long userId,
            int year,
            int month) {

        // 指定年月の月初日を取得する。
        LocalDate startDate = LocalDate.of(year, month, 1);
        // 翌月の月初日を取得する。
        LocalDate endDate = startDate.plusMonths(1);
        return expenseMapper.findVariableExpenses(
                userId,
                startDate,
                endDate
        );
    }

    // 変動費登録
    public void createVariableExpense(Long userId, ExpenseRequest request) {
        ExpenseTransaction expense = new ExpenseTransaction();
        expense.setUserId(userId);
        expense.setTransactionDate(request.getTransactionDate());
        expense.setExpenseType("VARIABLE");
        expense.setCategoryId(request.getCategoryId());
        expense.setPaymentMethodId(request.getPaymentMethodId());
        expense.setFixedExpenseTemplateId(null);
        expense.setAmount(request.getAmount());
        expense.setTransactionName(request.getName());
        expense.setShopName(request.getShopName());
        expense.setMemo(request.getMemo());
        LocalDateTime now = LocalDateTime.now();
        expense.setCreatedAt(now);
        expense.setUpdatedAt(now);
        expenseTransactionRepository.save(expense);
    }

    // 指定した変動費を更新する。
    public void updateVariableExpense(Long userId, Long id, ExpenseRequest request) {

        // 指定した変動費を更新する。
        int updatedCount = expenseMapper.updateVariableExpense(
                userId,
                id,
                request.getTransactionDate(),
                request.getCategoryId(),
                request.getPaymentMethodId(),
                request.getAmount(),
                request.getName(),
                request.getShopName(),
                request.getMemo()
        );

        // 更新対象が存在しない場合は404を返す。
        if (updatedCount == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "更新対象の変動費が見つかりません。"
            );
        }
    }

    // 指定した変動費を物理削除する。
    public void deleteVariableExpense(Long userId, Long id) {

        // 削除処理を実行する。
        int deletedCount = expenseMapper.deleteVariableExpense(userId, id);

        // 削除対象が存在しない場合は404を返す。
        if (deletedCount == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "削除対象の変動費が見つかりません。"
            );
        }
    }
}