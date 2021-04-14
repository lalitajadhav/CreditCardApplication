package com.example.CreditCardApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    public boolean isValidCardNumber(String cardNumber) {
        int[] numberLength = {cardNumber.length() % 2 == 0 ? 1 : 2};        // 1 if length even, 2 otherwise
        return cardNumber.chars()
                .map(i -> i - '0')                         // convert to the int equivalent
                .map(n -> n * (numberLength[0] = numberLength[0] == 1 ? 2 : 1)) // multiply by 1, 2 alternating
                .map(n -> n > 9 ? n - 9 : n)              // handle sum of digits
                .sum() % 10 == 0;                         // mod 10 should be zero
    }
}
