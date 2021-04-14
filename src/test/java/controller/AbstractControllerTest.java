package controller;

import com.example.CreditCardApplication.persistence.Repositories.CardRepository;
import com.example.CreditCardApplication.service.CreditCardService;
import com.example.CreditCardApplication.service.ValidationService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AbstractControllerTest {
    @Mock
    protected CreditCardService creditCardService;

    @Mock
    protected ValidationService validationService;

    @Mock
    protected CardRepository cardRepository;

    @Autowired
    protected MockMvc mvc;
}
