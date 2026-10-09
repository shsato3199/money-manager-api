-- ============================================================
-- 固定費支出の重複登録防止
-- ============================================================

-- 同一固定費設定について、同じ年月の支出は1件のみ登録可能とする。
CREATE UNIQUE INDEX uq_expense_transactions_template_month
    ON expense_transactions (
                             fixed_expense_template_id,
                             EXTRACT(YEAR FROM transaction_date),
                             EXTRACT(MONTH FROM transaction_date)
        )
    WHERE fixed_expense_template_id IS NOT NULL;