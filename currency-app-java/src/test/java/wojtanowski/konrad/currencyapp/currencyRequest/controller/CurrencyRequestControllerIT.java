package wojtanowski.konrad.currencyapp.currencyRequest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;
import wojtanowski.konrad.currencyapp.currencyRequest.repository.CurrencyRequestRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyRequestControllerIT {

    @Autowired
    CurrencyRequestController currencyRequestController;

    @Autowired
    CurrencyRequestRepository currencyRequestRepository;

    @Test
    void testGetAllCurrencyRequestsEmpty() {
        GetCurrencyRequestsDTO response = currencyRequestController.getAllCurrencies().getBody();

        assertThat(response).isNotNull();
        assertThat(response.currencyRequests()).isNotNull();
        assertThat(response.currencyRequests().size()).isEqualTo(0);
    }

    @Rollback
    @Transactional
    @Test
    void testGetAllCurrencyRequestsNotEmpty() {
        currencyRequestRepository.save(new CurrencyRequest("EUR", "JAN KOWALSKI", 4.6F));

        GetCurrencyRequestsDTO response = currencyRequestController.getAllCurrencies().getBody();

        assertThat(response).isNotNull();
        assertThat(response.currencyRequests()).isNotNull();
        assertThat(response.currencyRequests().size()).isEqualTo(1);
    }
}