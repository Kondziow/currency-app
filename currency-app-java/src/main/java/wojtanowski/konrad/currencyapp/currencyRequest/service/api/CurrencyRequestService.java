package wojtanowski.konrad.currencyapp.currencyRequest.service.api;

import jakarta.validation.Valid;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyValueDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;

public interface CurrencyRequestService {
    GetCurrencyRequestsDTO getAllCurrencyRequests();
    GetCurrencyValueDTO postCurrencyRequest(@Valid PostCurrencyRequestDTO currencyRequest) throws Exception;
    void saveCurrencyRequest(PostCurrencyRequestDTO currencyRequest, Float currencyValue);

}
