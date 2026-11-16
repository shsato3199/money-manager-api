package com.shsato.moneymanagerapi.paymentmethod.service;

import com.shsato.moneymanagerapi.paymentmethod.dto.PaymentMethodRequest;
import com.shsato.moneymanagerapi.paymentmethod.dto.PaymentMethodResponse;
import com.shsato.moneymanagerapi.paymentmethod.entity.PaymentMethod;
import com.shsato.moneymanagerapi.paymentmethod.repository.PaymentMethodRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository repository;

    public PaymentMethodService(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    // 支払元一覧取得。
    @Transactional(readOnly = true)
    public List<PaymentMethodResponse> getPaymentMethods(Long userId) {

        return repository.findByUserIdAndActiveTrueOrderByDisplayOrderAscIdAsc(userId)
                .stream()
                .map(paymentMethod -> new PaymentMethodResponse(
                        paymentMethod.getId(),
                        paymentMethod.getName(),
                        paymentMethod.getPaymentType(),
                        paymentMethod.getDisplayOrder()
                ))
                .toList();
    }

    // 支払元登録。
    @Transactional
    public void createPaymentMethod(Long userId, PaymentMethodRequest request) {

        // 支払元名をチェックする。
        if (request == null || request.name() == null
                || request.name().isBlank()
                || request.name().trim().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "支払元名が不正です。"
            );
        }

        String name = request.name().trim();

        // 支払元種別をチェックする。
        if (request.paymentType() == null
                || !List.of("CASH", "BANK_ACCOUNT", "CREDIT_CARD", "OTHER")
                .contains(request.paymentType())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "支払元種別が不正です。"
            );
        }

        // 支払元名の重複チェック。
        if (repository.existsByUserIdAndNameAndActiveTrue(userId, name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "同じ名前の支払元が既に登録されています。"
            );
        }
        // 新規登録時の表示順を取得する。
        // 論理削除済みの支払元は除外する。
        List<PaymentMethod> paymentMethods =
                repository.findByUserIdAndActiveTrueOrderByDisplayOrderDesc(userId);

        int displayOrder = paymentMethods.isEmpty()
                ? 1
                : paymentMethods.get(0).getDisplayOrder() + 1;

        OffsetDateTime now = OffsetDateTime.now();

        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.setUserId(userId);
        paymentMethod.setName(name);
        paymentMethod.setPaymentType(request.paymentType());
        paymentMethod.setDisplayOrder(displayOrder);
        paymentMethod.setActive(true);
        paymentMethod.setCreatedAt(now);
        paymentMethod.setUpdatedAt(now);

        repository.save(paymentMethod);
    }

    // 支払元更新。
    @Transactional
    public PaymentMethodResponse updatePaymentMethod(
            Long userId,
            Long id,
            PaymentMethodRequest request
    ) {

        // 更新対象の支払元を取得する。
        PaymentMethod paymentMethod = repository
                .findByIdAndUserIdAndActiveTrue(id, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "支払元が見つかりません。"
                ));

        // 支払元名をチェックする。
        if (request == null || request.name() == null
                || request.name().isBlank()
                || request.name().trim().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "支払元名が不正です。"
            );
        }

        String name = request.name().trim();

        // 支払元種別をチェックする。
        if (request.paymentType() == null
                || !List.of("CASH", "BANK_ACCOUNT", "CREDIT_CARD", "OTHER")
                .contains(request.paymentType())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "支払元種別が不正です。"
            );
        }

        // 更新対象自身を除いた支払元名の重複チェック。
        if (repository.existsByUserIdAndNameAndActiveTrueAndIdNot(
                userId,
                name,
                id
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "同じ名前の支払元が既に登録されています。"
            );
        }

        // 有効な支払元一覧を表示順で取得する。
        List<PaymentMethod> paymentMethods =
                repository.findByUserIdAndActiveTrueOrderByDisplayOrderAscIdAsc(userId);

        // 現在の表示順を取得する。
        int currentDisplayOrder = -1;

        for (int i = 0; i < paymentMethods.size(); i++) {
            if (paymentMethods.get(i).getId().equals(id)) {
                currentDisplayOrder = i + 1;
                break;
            }
        }

        if (currentDisplayOrder == -1) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "支払元が見つかりません。"
            );
        }

        // 表示順が指定されていない場合は現在の表示順を使用する。
        int newDisplayOrder = request.displayOrder() == null
                ? currentDisplayOrder
                : request.displayOrder();

        // 表示順の入力チェック。
        if (newDisplayOrder < 1 || newDisplayOrder > paymentMethods.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "表示順が不正です。"
            );
        }

        // 更新対象の支払元を一覧から一度除外する。
        paymentMethods.removeIf(
                item -> item.getId().equals(id)
        );

        // 指定された表示順の位置に挿入する。
        paymentMethods.add(newDisplayOrder - 1, paymentMethod);

        OffsetDateTime now = OffsetDateTime.now();

        // すべての支払元の表示順を振り直す。
        for (int i = 0; i < paymentMethods.size(); i++) {

            PaymentMethod item = paymentMethods.get(i);

            int displayOrder = i + 1;

            // 表示順が変更された場合のみ更新する。
            if (!Integer.valueOf(displayOrder).equals(item.getDisplayOrder())) {
                item.setDisplayOrder(displayOrder);
                item.setUpdatedAt(now);
            }
        }

        // 支払元名・支払元種別・更新日時を変更する。
        paymentMethod.setName(name);
        paymentMethod.setPaymentType(request.paymentType());
        paymentMethod.setUpdatedAt(now);

        // 支払元一覧を更新する。
        repository.saveAll(paymentMethods);

        return new PaymentMethodResponse(
                paymentMethod.getId(),
                paymentMethod.getName(),
                paymentMethod.getPaymentType(),
                paymentMethod.getDisplayOrder()
        );
    }

    // 支払元論理削除。
    @Transactional
    public void deletePaymentMethod(Long userId, Long id) {

        // 削除対象の支払元を取得する。
        PaymentMethod paymentMethod = repository
                .findByIdAndUserIdAndActiveTrue(id, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "支払元が見つかりません。"
                ));

        // 有効な支払元一覧を取得する。
        List<PaymentMethod> paymentMethods =
                repository.findByUserIdAndActiveTrueOrderByDisplayOrderAscIdAsc(userId);

        // 支払元は最低1件残す。
        if (paymentMethods.size() <= 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "最後の支払元は削除できません。"
            );
        }

        OffsetDateTime now = OffsetDateTime.now();

        // 論理削除する。
        paymentMethod.setActive(false);
        paymentMethod.setUpdatedAt(now);

        // 削除対象を一覧から除外する。
        paymentMethods.removeIf(
                item -> item.getId().equals(id)
        );

        // 削除後の表示順を振り直す。
        for (int i = 0; i < paymentMethods.size(); i++) {

            PaymentMethod item = paymentMethods.get(i);

            int displayOrder = i + 1;

            // 表示順が変更された場合のみ更新する。
            if (!Integer.valueOf(displayOrder).equals(item.getDisplayOrder())) {
                item.setDisplayOrder(displayOrder);
                item.setUpdatedAt(now);
            }
        }

        // 削除対象を更新する。
        repository.save(paymentMethod);

        // 残った支払元の表示順を更新する。
        repository.saveAll(paymentMethods);
    }
}