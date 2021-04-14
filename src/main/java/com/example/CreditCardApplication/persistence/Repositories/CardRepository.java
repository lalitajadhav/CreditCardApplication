package com.example.CreditCardApplication.persistence.Repositories;

import com.example.CreditCardApplication.persistence.Model.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardInfo, Long> {
    Optional<CardInfo> findByCardNumber(Long cardNumber);
}
