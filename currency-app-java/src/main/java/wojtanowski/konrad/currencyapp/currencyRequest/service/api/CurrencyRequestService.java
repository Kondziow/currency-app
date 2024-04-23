package wojtanowski.konrad.currencyapp.currencyRequest.service.api;

import jakarta.validation.Valid;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;

public interface CurrencyRequestService {
    GetCurrencyRequestsDTO getAllCurrencyRequests();

    Float postCurrencyRequest(String currencyName, @Valid PostCurrencyRequestDTO currencyRequest) throws Exception;
}
