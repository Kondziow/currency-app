package wojtanowski.konrad.currencyapp.currencyRequest.controller;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyValueDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;
import wojtanowski.konrad.currencyapp.currencyRequest.repository.CurrencyRequestRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequest() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO("EUR", "JAN KOWALSKI");

        ResponseEntity<GetCurrencyValueDTO> response = currencyRequestController.postCurrencyRequest(currencyRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        CurrencyRequest found = currencyRequestRepository.findAll().get(0);

        //Testing saving into database
        assertThat(found.getCurrencyName()).isEqualTo(currencyRequest.currencyName());
        assertThat(found.getRequesterName()).isEqualTo(currencyRequest.requesterName());
        assertThat(found.getCurrencyValue()).isGreaterThan(0);

        //Testing response
        assertThat(response.getBody()).isNotNull();
        assertThat(found.getCurrencyValue()).isEqualTo(response.getBody().currencyValue());

        System.out.println(currencyRequestRepository.findAll().get(0).getCurrencyValue());
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequestNotFound() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO("EUT", "JAN KOWALSKI");

        assertThrows(ResponseStatusException.class, () ->currencyRequestController.postCurrencyRequest(currencyRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequestNullCurrencyName() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO(null, "JAN KOWALSKI");

        assertThrows(ConstraintViolationException.class, () -> currencyRequestController.postCurrencyRequest(currencyRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequestNullRequesterName() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO("EUR", null);

        assertThrows(ConstraintViolationException.class, () -> currencyRequestController.postCurrencyRequest(currencyRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequestCurrencyNameToShort() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO(
                "EU",
                "JAN KOWALSKI");

        assertThrows(ConstraintViolationException.class, () -> currencyRequestController.postCurrencyRequest(currencyRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequestCurrencyNameToLong() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO(
                "EURA",
                "JAN KOWALSKI");

        assertThrows(ConstraintViolationException.class, () -> currencyRequestController.postCurrencyRequest(currencyRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRequestRequesterNameToLong() {
        PostCurrencyRequestDTO currencyRequest = new PostCurrencyRequestDTO(
                "EUR",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        assertThrows(ConstraintViolationException.class, () -> currencyRequestController.postCurrencyRequest(currencyRequest));
    }
}