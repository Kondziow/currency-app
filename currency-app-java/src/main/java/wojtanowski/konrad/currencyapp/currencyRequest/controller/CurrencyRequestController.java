package wojtanowski.konrad.currencyapp.currencyRequest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.service.api.CurrencyRequestService;

@RestController
public class CurrencyRequestController {
    public static final String CURRENCY_PATH = "/currencies";
    public static final String CURRENCY_POST_PATH = CURRENCY_PATH + "/{currencyName}";
    public static final String CURRENCIES_GET_PATH = CURRENCY_PATH + "/requests";

    private final CurrencyRequestService currencyRequestService;

    @GetMapping(CURRENCIES_GET_PATH)
    public ResponseEntity<GetCurrencyRequestsDTO> getAllCurrencies() {
        return new ResponseEntity<>(currencyRequestService.getAllCurrencyRequests(), HttpStatus.OK) ;
    }

    @Autowired
    public CurrencyRequestController(CurrencyRequestService currencyRequestService) {
        this.currencyRequestService = currencyRequestService;
    }
}
