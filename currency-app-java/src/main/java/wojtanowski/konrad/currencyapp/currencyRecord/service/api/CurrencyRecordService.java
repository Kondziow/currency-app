package wojtanowski.konrad.currencyapp.currencyRecord.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordsResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;

public interface CurrencyRecordService {
    GetCurrencyRecordsResponse getAllCurrencyRecords();
    GetCurrencyValueResponse postCurrencyRecord(@Valid PostCurrencyRecordRequest currencyRecordRequest) throws JsonProcessingException;
    void saveCurrencyRecord(PostCurrencyRecordRequest currencyRecordRequest, Float currencyValue);
}
