package wojtanowski.konrad.currencyapp.currencyRecord.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wojtanowski.konrad.currencyapp.currencyRecord.model.entity.CurrencyRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CurrencyRecordRepositoryTest {
    @Autowired
    CurrencyRecordRepository currencyRecordRepository;

    @Test
    void testGetAllCurrencyRecordsEmpty() {
        List<CurrencyRecord> list = currencyRecordRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void testGetAllCurrencyRecordsNotEmpty() {
        CurrencyRecord currencyRecord = new CurrencyRecord("EUR", "Jan Kowalski", 4.87F);

        currencyRecordRepository.save(currencyRecord);
        currencyRecordRepository.flush();

        List<CurrencyRecord> list = currencyRecordRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1);

        System.out.println(list.get(0).getId());
        System.out.println(list.get(0).getCurrencyName());
        System.out.println(list.get(0).getRequesterName());
        System.out.println(list.get(0).getDate());
        System.out.println(list.get(0).getCurrencyValue());
    }

    @Test
    void testSaveCurrencyRecord() {
        CurrencyRecord currencyRecord = new CurrencyRecord("EUR", "Jan Kowalski", 4.87F);

        CurrencyRecord saved = currencyRecordRepository.save(currencyRecord);
        currencyRecordRepository.flush();

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDate()).isNotNull();
        assertThat(saved.getCurrencyName()).isEqualTo("EUR");
        assertThat(saved.getRequesterName()).isEqualTo("Jan Kowalski");
        assertThat(saved.getCurrencyValue()).isEqualTo(4.87F);
    }

    @Test
    void testSaveCurrencyRecordNullCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord(null, "Jan Kowalski", 4.87F));
            currencyRecordRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRecordBlankCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord("", "Jan Kowalski", 4.87F));
            currencyRecordRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRecordNullRequesterName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord("EUR", null, 4.87F));
            currencyRecordRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRecordBlankRequesterName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord("EUR", "", 4.87F));
            currencyRecordRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRecordTooLongCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord(
                    "aaaa",
                    "Jan Kowalski",
                    4.87F));
            currencyRecordRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRecordTooShortCurrencyName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord(
                    "aa",
                    "Jan Kowalski",
                    4.87F));
            currencyRecordRepository.flush();
        });
    }

    @Test
    void testSaveCurrencyRecordTooLongRequesterName() {
        assertThrows(ConstraintViolationException.class, () -> {
            currencyRecordRepository.save(new CurrencyRecord(
                    "EUR",
                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    4.87F));
            currencyRecordRepository.flush();
        });
    }
}