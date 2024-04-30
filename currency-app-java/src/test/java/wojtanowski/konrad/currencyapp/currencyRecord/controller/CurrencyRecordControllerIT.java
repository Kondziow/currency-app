package wojtanowski.konrad.currencyapp.currencyRecord.controller;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordsResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;
import wojtanowski.konrad.currencyapp.currencyRecord.model.entity.CurrencyRecord;
import wojtanowski.konrad.currencyapp.currencyRecord.repository.CurrencyRecordRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CurrencyRecordControllerIT {

    @Autowired
    CurrencyRecordController currencyRecordController;

    @Autowired
    CurrencyRecordRepository currencyRecordRepository;

    @Test
    void testGetAllCurrencyRecordsEmpty() {
        GetCurrencyRecordsResponse response = currencyRecordController.getAllCurrencies().getBody();

        assertThat(response).isNotNull();
        assertThat(response.currencyRecords()).isNotNull();
        assertThat(response.currencyRecords().size()).isEqualTo(0);
    }

    @Rollback
    @Transactional
    @Test
    void testGetAllCurrencyRecordsNotEmpty() {
        currencyRecordRepository.save(new CurrencyRecord("EUR", "JAN KOWALSKI", 4.6F));

        GetCurrencyRecordsResponse response = currencyRecordController.getAllCurrencies().getBody();

        assertThat(response).isNotNull();
        assertThat(response.currencyRecords()).isNotNull();
        assertThat(response.currencyRecords().size()).isEqualTo(1);
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequest() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest("EUR", "JAN KOWALSKI");

        ResponseEntity<GetCurrencyValueResponse> response = currencyRecordController.postCurrencyRecord(currencyRecordRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        CurrencyRecord found = currencyRecordRepository.findAll().get(0);

        //Testing saving into database
        assertThat(found.getCurrencyName()).isEqualTo(currencyRecordRequest.currencyName());
        assertThat(found.getRequesterName()).isEqualTo(currencyRecordRequest.requesterName());
        assertThat(found.getCurrencyValue()).isGreaterThan(0);

        //Testing response
        assertThat(response.getBody()).isNotNull();
        assertThat(found.getCurrencyValue()).isEqualTo(response.getBody().currencyValue());

        System.out.println(currencyRecordRepository.findAll().get(0).getCurrencyValue());
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequestNotFound() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest("EUT", "JAN KOWALSKI");

        assertThrows(ResponseStatusException.class, () -> currencyRecordController.postCurrencyRecord(currencyRecordRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequestNullCurrencyName() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest(null, "JAN KOWALSKI");

        assertThrows(ConstraintViolationException.class, () -> currencyRecordController.postCurrencyRecord(currencyRecordRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequestNullRequesterName() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest("EUR", null);

        assertThrows(ConstraintViolationException.class, () -> currencyRecordController.postCurrencyRecord(currencyRecordRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequestCurrencyNameToShort() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest(
                "EU",
                "JAN KOWALSKI");

        assertThrows(ConstraintViolationException.class, () -> currencyRecordController.postCurrencyRecord(currencyRecordRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequestCurrencyNameToLong() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest(
                "EURA",
                "JAN KOWALSKI");

        assertThrows(ConstraintViolationException.class, () -> currencyRecordController.postCurrencyRecord(currencyRecordRequest));
    }

    @Rollback
    @Transactional
    @Test
    void testPostCurrencyRecordRequestRequesterNameToLong() {
        PostCurrencyRecordRequest currencyRecordRequest = new PostCurrencyRecordRequest(
                "EUR",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        assertThrows(ConstraintViolationException.class, () -> currencyRecordController.postCurrencyRecord(currencyRecordRequest));
    }
}