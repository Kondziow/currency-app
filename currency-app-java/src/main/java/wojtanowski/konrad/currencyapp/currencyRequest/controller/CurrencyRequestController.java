package wojtanowski.konrad.currencyapp.currencyRequest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyValueDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.service.api.CurrencyRequestService;

@RestController
public class CurrencyRequestController {
    public static final String CURRENCY_PATH = "/currencies";
    public static final String CURRENCY_POST_PATH = CURRENCY_PATH + "/{currencyName}";
    public static final String CURRENCIES_GET_PATH = CURRENCY_PATH + "/requests";

    private final CurrencyRequestService currencyRequestService;

    @GetMapping(CURRENCIES_GET_PATH)
    public ResponseEntity<GetCurrencyRequestsDTO> getAllCurrencies() {
        return new ResponseEntity<>(currencyRequestService.getAllCurrencyRequests(), HttpStatus.OK);
    }

    @PostMapping(CURRENCY_POST_PATH)
    public ResponseEntity<GetCurrencyValueDTO> postCurrencyRequest(
            @PathVariable("currencyName") String currencyName,
            @RequestBody PostCurrencyRequestDTO currencyRequest
    ) {
        GetCurrencyValueDTO responseDTO;
        try {
            responseDTO = currencyRequestService.postCurrencyRequest(currencyName, currencyRequest);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        Float response = responseDTO.currencyValue();

        if(response < 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        currencyRequestService.saveCurrencyRequest(currencyRequest, response);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Autowired
    public CurrencyRequestController(CurrencyRequestService currencyRequestService) {
        this.currencyRequestService = currencyRequestService;
    }
}
