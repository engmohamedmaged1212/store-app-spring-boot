package com.codewithmosh.store.Controllers;

import com.codewithmosh.store.dtos.CheckoutRequestDto;
import com.codewithmosh.store.services.CheckOutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("checkout")
public class CheckoutController {
    private final CheckOutService checkOutService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequestDto request){
        return ResponseEntity.ok(checkOutService.checkout(request));
    }
}
