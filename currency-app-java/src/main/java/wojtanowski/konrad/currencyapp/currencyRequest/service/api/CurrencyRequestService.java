package wojtanowski.konrad.currencyapp.currencyRequest.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyValueDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;

public interface CurrencyRequestService {
    GetCurrencyRequestsDTO getAllCurrencyRequests();
    GetCurrencyValueDTO postCurrencyRequest(@Valid PostCurrencyRequestDTO currencyRequest) throws JsonProcessingException;
    void saveCurrencyRequest(PostCurrencyRequestDTO currencyRequest, Float currencyValue);

}
