package wojtanowski.konrad.currencyapp.currencyRecord.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;

public interface CurrencyRecordService {
    Page<GetCurrencyRecordResponse> getAllCurrencyRecords(Integer pageNumber, Integer pageSize);
    GetCurrencyValueResponse postCurrencyRecord(@Valid PostCurrencyRecordRequest currencyRecordRequest) throws JsonProcessingException;
    void saveCurrencyRecord(PostCurrencyRecordRequest currencyRecordRequest, Float currencyValue);
}
