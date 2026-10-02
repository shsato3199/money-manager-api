-- ============================================================
-- 家計簿アプリ テーブル作成
-- Flyway Migration: V1__create_tables.sql
-- ============================================================
-- ============================================================
-- 1. users
-- ユーザー
-- ============================================================
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    google_sub VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(100),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);
-- ============================================================
-- 2. payment_methods
-- 支払元
-- ============================================================
CREATE TABLE payment_methods (
    payment_method_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    payment_method_name VARCHAR(100) NOT NULL,
    payment_type VARCHAR(30) NOT NULL,
    display_order INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_payment_methods_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT uq_payment_methods_user_name
        UNIQUE (user_id, payment_method_name)
);
-- ============================================================
-- 3. expense_categories
-- 支出カテゴリ
-- ============================================================
CREATE TABLE expense_categories (
    category_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    display_order INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_expense_categories_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT uq_expense_categories_user_name
        UNIQUE (user_id, category_name)
);
-- ============================================================
-- 4. fixed_expense_templates
-- 固定費設定
-- ============================================================
CREATE TABLE fixed_expense_templates (
    fixed_expense_template_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    fixed_expense_name VARCHAR(100) NOT NULL,
    amount INTEGER NOT NULL,
    payment_day INTEGER NOT NULL,
    start_year_month CHAR(7) NOT NULL,
    end_year_month CHAR(7),
    auto_generate BOOLEAN NOT NULL,
    memo VARCHAR(500),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_fixed_expense_templates_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT fk_fixed_expense_templates_category
        FOREIGN KEY (category_id)
        REFERENCES expense_categories(category_id),

    CONSTRAINT fk_fixed_expense_templates_payment_method
        FOREIGN KEY (payment_method_id)
        REFERENCES payment_methods(payment_method_id)
);
-- ============================================================
-- 5. expense_transactions
-- 支出明細
-- ============================================================
CREATE TABLE expense_transactions (
    transaction_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL,
    expense_type VARCHAR(20) NOT NULL,
    category_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    fixed_expense_template_id BIGINT,
    amount INTEGER NOT NULL,
    transaction_name VARCHAR(255),
    shop_name VARCHAR(255),
    memo VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_expense_transactions_user
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT fk_expense_transactions_category
        FOREIGN KEY (category_id)
        REFERENCES expense_categories(category_id),

    CONSTRAINT fk_expense_transactions_payment_method
        FOREIGN KEY (payment_method_id)
        REFERENCES payment_methods(payment_method_id),

    CONSTRAINT fk_expense_transactions_fixed_expense_template
        FOREIGN KEY (fixed_expense_template_id)
        REFERENCES fixed_expense_templates(fixed_expense_template_id)
);
-- ============================================================
-- 6. インデックス
-- ============================================================
CREATE INDEX idx_expense_transactions_user_id
    ON expense_transactions(user_id);

CREATE INDEX idx_expense_transactions_transaction_date
    ON expense_transactions(transaction_date);

CREATE INDEX idx_expense_transactions_category_id
    ON expense_transactions(category_id);

CREATE INDEX idx_expense_transactions_payment_method_id
    ON expense_transactions(payment_method_id);

CREATE INDEX idx_expense_transactions_fixed_expense_template_id
    ON expense_transactions(fixed_expense_template_id);

CREATE INDEX idx_payment_methods_user_id
    ON payment_methods(user_id);

CREATE INDEX idx_expense_categories_user_id
    ON expense_categories(user_id);

CREATE INDEX idx_fixed_expense_templates_user_id
    ON fixed_expense_templates(user_id);
