package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AddItemToCartRequestDto {
    @NotNull
    private long product_id;
}
