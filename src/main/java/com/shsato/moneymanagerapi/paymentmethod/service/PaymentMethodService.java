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

        return repository.findByUserIdAndActiveTrueOrderByDisplayOrderAsc(userId)
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
        if (request.name() == null || request.name().isBlank()
                || request.name().trim().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "支払元名が不正です。");
        }

        String name = request.name().trim();

        // 支払元種別をチェックする。
        if (request.paymentType() == null
                || !List.of("CASH", "BANK_ACCOUNT", "CREDIT_CARD", "OTHER")
                .contains(request.paymentType())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "支払元種別が不正です。");
        }

        // 支払元名の重複チェック。
        if (repository.existsByUserIdAndName(userId, name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "同じ名前の支払元が既に登録されています。");
        }

        // 新規登録時の表示順を取得する。
        List<PaymentMethod> paymentMethods =
                repository.findByUserIdOrderByDisplayOrderDesc(userId);

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
}