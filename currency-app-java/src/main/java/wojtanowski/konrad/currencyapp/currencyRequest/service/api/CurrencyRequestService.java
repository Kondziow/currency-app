package wojtanowski.konrad.currencyapp.currencyRequest.service.api;

import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;

public interface CurrencyRequestService {
    GetCurrencyRequestsDTO getAllCurrencyRequests();
}
