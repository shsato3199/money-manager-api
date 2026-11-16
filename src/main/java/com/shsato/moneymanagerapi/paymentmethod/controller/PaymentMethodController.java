package com.shsato.moneymanagerapi.paymentmethod.controller;

import com.shsato.moneymanagerapi.paymentmethod.dto.PaymentMethodRequest;
import com.shsato.moneymanagerapi.paymentmethod.dto.PaymentMethodResponse;
import com.shsato.moneymanagerapi.paymentmethod.service.PaymentMethodService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    // 支払元一覧取得。
    @GetMapping
    public List<PaymentMethodResponse> getPaymentMethods() {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return paymentMethodService.getPaymentMethods(userId);
    }

    // 支払元登録。
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPaymentMethod(@RequestBody PaymentMethodRequest request) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        paymentMethodService.createPaymentMethod(userId, request);
    }

    // 支払元更新。
    @PutMapping("/{id}")
    public PaymentMethodResponse updatePaymentMethod(
            @PathVariable Long id,
            @RequestBody PaymentMethodRequest request
    ) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        return paymentMethodService.updatePaymentMethod(userId, id, request);
    }

    // 支払元論理削除。
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentMethod(@PathVariable Long id) {

        // TODO: Googleログイン実装後はSessionからuserIdを取得する。
        Long userId = 1L;

        paymentMethodService.deletePaymentMethod(userId, id);
    }
}