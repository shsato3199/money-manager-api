-- ============================================================
-- V4：支払元名の重複制約変更
-- 論理削除された支払元と同じ名前を再登録できるようにする。
-- ============================================================

-- 既存のUNIQUE制約を削除する。
ALTER TABLE payment_methods
DROP CONSTRAINT uq_payment_methods_user_name;

-- 有効な支払元のみ、ユーザーごとの支払元名重複を禁止する。
CREATE UNIQUE INDEX uq_payment_methods_active_name
    ON payment_methods(user_id, payment_method_name)
    WHERE is_active = true;