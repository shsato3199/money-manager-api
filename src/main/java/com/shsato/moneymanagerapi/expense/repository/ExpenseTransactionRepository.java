package com.shsato.moneymanagerapi.expense.repository;

import com.shsato.moneymanagerapi.expense.entity.ExpenseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTransactionRepository
        extends JpaRepository<ExpenseTransaction, Long> {
}