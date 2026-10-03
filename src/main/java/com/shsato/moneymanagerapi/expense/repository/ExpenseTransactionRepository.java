package com.shsato.moneymanagerapi.expense.repository;

import com.shsato.moneymanagerapi.expense.entity.ExpenseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction, Long> {

    /**
     *SELECT *
     * FROM expense_transactions
     * WHERE user_id = ?
     *   AND expense_type = ?
     * ORDER BY transaction_date DESC;
     * **/
    List<ExpenseTransaction> findByUserIdAndExpenseTypeOrderByTransactionDateDesc(
            Long userId,
            String expenseType
    );
}