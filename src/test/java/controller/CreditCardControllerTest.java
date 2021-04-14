package controller;

import com.example.CreditCardApplication.CreditCardApplication;
import com.example.CreditCardApplication.persistence.Model.CardInfo;
import com.example.CreditCardApplication.requestDTO.CardRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static com.example.CreditCardApplication.Utils.Constants.API_VERSION_1_0_0;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = CreditCardApplication.class)
public class CreditCardControllerTest extends AbstractControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void saveCardInformation() throws Exception
    {
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.setCardNumber("79927398713");
        requestDTO.setName("lalita Jadhav");
        requestDTO.setLimit(1000L);

        this.mvc.perform(MockMvcRequestBuilders.post(API_VERSION_1_0_0 + "/cards/add")
                                               .content(mapper.writeValueAsString(requestDTO)).contentType(MediaType.APPLICATION_JSON)
                                               ).andExpect(status().isOk());

    }

    @Test
    public void saveCardInformationInvalidCard() throws Exception
    {
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.setCardNumber("123");
        requestDTO.setName("lalita Jadhav");
        requestDTO.setLimit(1000L);
        BindingResult errors = new BeanPropertyBindingResult(null, "");
        when(creditCardService.validateCardRequest(requestDTO,errors)).thenReturn(true);
        when(validationService.isValidCardNumber(requestDTO.getCardNumber())).thenReturn(false);
        doCallRealMethod().when(creditCardService).processCardInformation(requestDTO,errors);
        this.mvc.perform(MockMvcRequestBuilders.post(API_VERSION_1_0_0 + "/cards/add")
                .content(mapper.writeValueAsString(requestDTO)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());

    }

    @Test
    public  void getAll() throws  Exception
    {
        CardInfo objCardInfo = new CardInfo();
        objCardInfo.setCardNumber(123L);
        objCardInfo.setBalance(0L);
        objCardInfo.setLimit(1000L);
        objCardInfo.setName("test");
        List<CardInfo> list= new ArrayList<>();
        list.add(objCardInfo);
        when(cardRepository.findAll()).thenReturn(list);
        when(creditCardService.getAll()).thenReturn(list);
        this.mvc.perform(MockMvcRequestBuilders.get(API_VERSION_1_0_0 + "/cards/getAll")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
