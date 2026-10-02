-- ============================================================
-- 1. users
-- ユーザー
-- ============================================================
INSERT INTO users (
    google_sub,
    email,
    display_name,
    is_active,
    created_at,
    updated_at
) VALUES (
    'test-google-sub-001',
    'test@example.com',
    'テストユーザー',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
-- BIGSERIAL のuser_idは自動採番されるので不要

-- ============================================================
-- 2. payment_methods
-- 支払元
-- ============================================================
INSERT INTO payment_methods (
    user_id,
    payment_method_name,
    payment_type,
    display_order,
    is_active,
    created_at,
    updated_at
) VALUES (
    1,
    '現金',
    'CASH',
    1,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================================
-- 3. expense_categories
-- 支出カテゴリ
-- ============================================================
INSERT INTO expense_categories (
    user_id,
    category_name,
    display_order,
    is_active,
    created_at,
    updated_at
) VALUES (
    1,
    '通信費',
    1,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================================
-- 4. fixed_expense_templates
-- 固定費設定
-- ============================================================
INSERT INTO fixed_expense_templates (
    user_id,
    category_id,
    payment_method_id,
    fixed_expense_name,
    amount,
    payment_day,
    start_year_month,
    end_year_month,
    auto_generate,
    memo,
    is_active,
    created_at,
    updated_at
) VALUES (
    1,
    1,
    1,
    'スマホ料金',
    4000,
    10,
    '2026-10',
    NULL,
    true,
    'テスト用固定費',
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================================
-- 5. expense_transactions
-- 支出明細
-- ============================================================
INSERT INTO expense_transactions (
    user_id,
    transaction_date,
    expense_type,
    category_id,
    payment_method_id,
    fixed_expense_template_id,
    amount,
    transaction_name,
    shop_name,
    memo,
    created_at,
    updated_at
) VALUES (
    1,
    '2026-10-10',
    'FIXED',
    1,
    1,
    1,
    4000,
    'スマホ料金',
    NULL,
    'テスト用支出明細',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
