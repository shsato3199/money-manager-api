package com.shsato.moneymanagerapi.category.dto;

public record CategoryResponse(
        Long id,
        String name,
        Integer displayOrder
) {
}