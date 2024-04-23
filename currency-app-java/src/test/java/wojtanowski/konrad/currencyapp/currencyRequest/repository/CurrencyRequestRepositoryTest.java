package wojtanowski.konrad.currencyapp.currencyRequest.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CurrencyRequestRepositoryTest {
    @Autowired
    CurrencyRequestRepository currencyRequestRepository;

    @Test
    void testGetAllCurrencyRequestsEmpty() {
        List<CurrencyRequest> list = currencyRequestRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void testGetAllCurrencyRequestsNotEmpty() {
        CurrencyRequest currencyRequest = new CurrencyRequest("EUR", "Jan Kowalski", 4.87F);

        currencyRequestRepository.save(currencyRequest);
        currencyRequestRepository.flush();

        List<CurrencyRequest> list = currencyRequestRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);

        System.out.println(list.get(0).getId());
        System.out.println(list.get(0).getCurrencyName());
        System.out.println(list.get(0).getRequesterName());
        System.out.println(list.get(0).getDate());
        System.out.println(list.get(0).getCurrencyValue());
    }

    @Test
    void testGetCurrencyRequestById() {
        CurrencyRequest currencyRequest = new CurrencyRequest("EUR", "Jan Kowalski", 4.87F);

        CurrencyRequest saved = currencyRequestRepository.save(currencyRequest);
        currencyRequestRepository.flush();

        CurrencyRequest found = currencyRequestRepository.findById(saved.getId()).get();

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getCurrencyName()).isEqualTo(saved.getCurrencyName());
        assertThat(found.getRequesterName()).isEqualTo(saved.getRequesterName());
        assertThat(found.getDate()).isEqualTo(saved.getDate());
        assertThat(found.getCurrencyValue()).isEqualTo(saved.getCurrencyValue());
    }

    @Test
    void testSaveCurrencyRequest() {
        CurrencyRequest currencyRequest = new CurrencyRequest("EUR", "Jan Kowalski", 4.87F);

        CurrencyRequest saved = currencyRequestRepository.save(currencyRequest);
        currencyRequestRepository.flush();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDate()).isNotNull();
        assertThat(saved.getCurrencyName()).isEqualTo("EUR");
        assertThat(saved.getRequesterName()).isEqualTo("Jan Kowalski");
        assertThat(saved.getCurrencyValue()).isEqualTo(4.87F);
    }

    @Test
    void testSaveCurrencyRequestNullCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest(null, "Jan Kowalski", 4.87F));
            currencyRequestRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRequestBlankCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest("", "Jan Kowalski", 4.87F));
            currencyRequestRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRequestNullRequesterName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest("EUR", null, 4.87F));
            currencyRequestRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRequestBlankRequesterName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest("EUR", "", 4.87F));
            currencyRequestRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRequestTooLongCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest(
                    "aaaa",
                    "Jan Kowalski",
                    4.87F));
            currencyRequestRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRequestTooShortCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest(
                    "aa",
                    "Jan Kowalski",
                    4.87F));
            currencyRequestRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRequestTooLongRequesterName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRequestRepository.save(new CurrencyRequest(
                    "EUR",
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    4.87F));
            currencyRequestRepository.flush();
        });
    }
}