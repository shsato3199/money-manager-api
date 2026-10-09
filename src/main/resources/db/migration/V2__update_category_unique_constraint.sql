-- ============================================================
-- V2：カテゴリ名の重複制約変更
-- 論理削除されたカテゴリと同じ名前を再登録できるようにする。
-- ============================================================

-- 既存のUNIQUE制約を削除する。
ALTER TABLE expense_categories
DROP CONSTRAINT uq_expense_categories_user_name;

-- 有効なカテゴリのみ、ユーザーごとのカテゴリ名重複を禁止する。
CREATE UNIQUE INDEX uq_expense_categories_active_name
    ON expense_categories(user_id, category_name)
    WHERE is_active = true;