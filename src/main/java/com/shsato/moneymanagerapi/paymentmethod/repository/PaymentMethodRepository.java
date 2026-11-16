package com.shsato.moneymanagerapi.paymentmethod.repository;

import com.shsato.moneymanagerapi.paymentmethod.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository
        extends JpaRepository<PaymentMethod, Long> {

    // 有効な支払元を表示順・支払元ID順で取得する。
    List<PaymentMethod> findByUserIdAndActiveTrueOrderByDisplayOrderAscIdAsc(
            Long userId
    );

    // 同一ユーザーの支払元名の重複を確認する。
    boolean existsByUserIdAndName(
            Long userId,
            String name
    );

    // 新規登録時の表示順を決定するために使用する。
    // 論理削除済みの支払元は対象外とする。
    List<PaymentMethod> findByUserIdAndActiveTrueOrderByDisplayOrderDesc(
            Long userId
    );

    // 有効な支払元をユーザーIDと支払元IDで取得する。
    Optional<PaymentMethod> findByIdAndUserIdAndActiveTrue(
            Long id,
            Long userId
    );
    // 同じユーザーに同名の有効な支払元が存在するか確認する。
    boolean existsByUserIdAndNameAndActiveTrue(
            Long userId,
            String name
    );
    // 更新対象自身を除いて、同名の有効な支払元が存在するか確認する。
    boolean existsByUserIdAndNameAndActiveTrueAndIdNot(
            Long userId,
            String name,
            Long id
    );
}