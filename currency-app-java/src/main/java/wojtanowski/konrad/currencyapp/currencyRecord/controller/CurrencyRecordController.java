package wojtanowski.konrad.currencyapp.currencyRecord.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordsResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;
import wojtanowski.konrad.currencyapp.currencyRecord.service.api.CurrencyRecordService;


@RestController
public class CurrencyRecordController {
    private static final String CURRENCY_PATH = "/api/currencies";
    public static final String CURRENCY_POST_PATH = CURRENCY_PATH + "/get-current-currency-value-command";
    public static final String CURRENCIES_GET_PATH = CURRENCY_PATH + "/requests";

    private final CurrencyRecordService currencyRecordService;

    @GetMapping(CURRENCIES_GET_PATH)
    public ResponseEntity<GetCurrencyRecordsResponse> getAllCurrencies() {
        return new ResponseEntity<>(currencyRecordService.getAllCurrencyRecords(), HttpStatus.OK);
    }

    @PostMapping(CURRENCY_POST_PATH)
    public ResponseEntity<GetCurrencyValueResponse> postCurrencyRecord(
            @Validated @RequestBody PostCurrencyRecordRequest currencyRecordRequest
    ) {
        GetCurrencyValueResponse responseDTO;
        try {
            responseDTO = currencyRecordService.postCurrencyRecord(currencyRecordRequest);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        Float response = responseDTO.currencyValue();

        if(response < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        currencyRecordService.saveCurrencyRecord(currencyRecordRequest, response);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Autowired
    public CurrencyRecordController(CurrencyRecordService currencyRecordService) {
        this.currencyRecordService = currencyRecordService;
    }
}
