package com.example.CreditCardApplication.service;

import com.example.CreditCardApplication.persistence.Model.CardInfo;
import com.example.CreditCardApplication.persistence.Repositories.CardRepository;
import com.example.CreditCardApplication.requestDTO.CardRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditCardService {

    private ValidationService validationService;
    private CardRepository cardRepository;

    public static final String CARD_NUMBER_NOT_PRESENT = "Request does not contain card number";
    public static final String CARD_NUMBER_NOT_NUMERIC = "Card Number is not numeric";
    public static final String INVALID_CARD_NUMBER = "Card Number is not valid";

    public boolean validateCardRequest(CardRequestDTO requestDTO, BindingResult errors) {
        String cardNumber = requestDTO.getCardNumber();
        if (cardNumber == null) {
            addError(CARD_NUMBER_NOT_PRESENT, errors);
            return false;
        }
        if (!(cardNumber.matches("[0-9]+") && cardNumber.length() > 2)) {
            addError(CARD_NUMBER_NOT_NUMERIC, errors);
            return false;
        }
        return true;
    }

    private void addError(String message, BindingResult errors) {
        errors.reject(message);
    }

    public void processCardInformation(CardRequestDTO request, BindingResult errors) {
        Boolean isValid = validationService.isValidCardNumber(request.getCardNumber());
        if (isValid) {
            Optional<CardInfo> cardInfo = cardRepository.findByCardNumber(Long.parseLong(request.getCardNumber()));
            if (!cardInfo.isPresent()) {
                CardInfo objCardInfo = new CardInfo();
                objCardInfo.setCardNumber(Long.parseLong(request.getCardNumber()));
                objCardInfo.setBalance(0L);
                objCardInfo.setLimit(request.getLimit());
                objCardInfo.setName(request.getName());
                cardRepository.save(objCardInfo);
            }

        } else {
            addError(INVALID_CARD_NUMBER, errors);
        }
    }

    public List<CardInfo> getAll() {
        return cardRepository.findAll();
    }
}
