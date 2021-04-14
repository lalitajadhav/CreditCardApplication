package com.example.CreditCardApplication.service;

import com.example.CreditCardApplication.persistence.Model.CardInfo;
import com.example.CreditCardApplication.persistence.Repositories.CardRepository;
import com.example.CreditCardApplication.requestDTO.CardRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

import static com.example.CreditCardApplication.service.CreditCardService.CARD_NUMBER_NOT_NUMERIC;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceTest {

    @InjectMocks
    private CreditCardService creditCardService;

    @Mock
    private ValidationService validationService;

    @Mock
    private CardRepository cardRepository;

    @Test
    public void validateCardRequestTest() throws Exception
    {
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.setCardNumber("123qww");
        requestDTO.setName("lalita Jadhav");
        requestDTO.setLimit(1000L);
        BindingResult errors = new BeanPropertyBindingResult(null, "");
        creditCardService.validateCardRequest(requestDTO,errors);
        assertThat(errors.hasErrors()).isTrue();
        List<ObjectError> objectErrors =errors.getAllErrors();
        boolean isNonNumericError = false;
        for (ObjectError error:objectErrors) {
            isNonNumericError= Arrays.stream(error.getCodes()).anyMatch(code->code.equals(CARD_NUMBER_NOT_NUMERIC));
        }
        assertThat(isNonNumericError).isTrue();

    }

    @Test
    public void processCardInformationTest() throws Exception
    {
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.setCardNumber("123");
        requestDTO.setName("lalita Jadhav");
        requestDTO.setLimit(1000L);
        BindingResult errors = new BeanPropertyBindingResult(null, "");
        when(validationService.isValidCardNumber(requestDTO.getCardNumber())).thenReturn(true);

        creditCardService.processCardInformation(requestDTO,errors);
        CardInfo objCardInfo = new CardInfo();
        objCardInfo.setCardNumber(Long.parseLong(requestDTO.getCardNumber()));
        objCardInfo.setBalance(0L);
        objCardInfo.setLimit(requestDTO.getLimit());
        objCardInfo.setName(requestDTO.getName());
        verify(cardRepository,times(1)).save(objCardInfo);
    }


}
