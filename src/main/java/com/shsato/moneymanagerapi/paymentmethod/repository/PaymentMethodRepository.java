package com.shsato.moneymanagerapi.paymentmethod.repository;

import com.shsato.moneymanagerapi.paymentmethod.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository
        extends JpaRepository<PaymentMethod, Long> {

    // 有効な支払元を表示順で取得する。
    List<PaymentMethod> findByUserIdAndActiveTrueOrderByDisplayOrderAsc(Long userId);

    // 同一ユーザーの支払元名の重複を確認する。
    boolean existsByUserIdAndName(Long userId, String name);

    // 新規登録時の表示順を決定するために使用する。
    List<PaymentMethod> findByUserIdOrderByDisplayOrderDesc(Long userId);
}