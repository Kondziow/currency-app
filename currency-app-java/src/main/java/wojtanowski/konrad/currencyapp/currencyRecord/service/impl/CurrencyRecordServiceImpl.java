package wojtanowski.konrad.currencyapp.currencyRecord.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import wojtanowski.konrad.currencyapp.currencyRecord.client.api.CurrencyRecordClient;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordsResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;
import wojtanowski.konrad.currencyapp.currencyRecord.model.entity.CurrencyRecord;
import wojtanowski.konrad.currencyapp.currencyRecord.repository.CurrencyRecordRepository;
import wojtanowski.konrad.currencyapp.currencyRecord.service.api.CurrencyRecordService;

import java.util.stream.Collectors;

@Primary
@Service
@Validated
public class CurrencyRecordServiceImpl implements CurrencyRecordService {
    private final CurrencyRecordRepository currencyRecordRepository;
    private final CurrencyRecordClient client;
    private final ObjectMapper objectMapper;

    @Override
    public GetCurrencyRecordsResponse getAllCurrencyRecords() {
        return new GetCurrencyRecordsResponse(currencyRecordRepository.findAll()
                .stream()
                .map(value -> new GetCurrencyRecordResponse(value.getCurrencyName(), value.getRequesterName(), value.getDate(), value.getCurrencyValue()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public GetCurrencyValueResponse postCurrencyRecord(PostCurrencyRecordRequest currencyRecordRequest) {
        try {
            String responseString = client.getCurrencies();
            Float responseValue = findCurrencyValue(responseString, currencyRecordRequest.currencyName());
            return new GetCurrencyValueResponse(responseValue);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("JSON processing error", ex);
        }
    }

    @Override
    public void saveCurrencyRecord(PostCurrencyRecordRequest currencyRecordRequest, Float currencyValue) {
        currencyRecordRepository.save(new CurrencyRecord(currencyRecordRequest.currencyName(), currencyRecordRequest.requesterName(), currencyValue));
    }

    Float findCurrencyValue(String jsonResponse, String currencyName) throws JsonProcessingException {
        float responseValue = -1F;
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        JsonNode element = jsonNode.get(0);
        JsonNode ratesArray = element.get("rates");

        for (JsonNode rate : ratesArray) {
            if (currencyName.equals(rate.get("code").asText())) {
                responseValue = rate.get("mid").floatValue();
                break;
            }
        }

        return responseValue;
    }

    @Autowired
    public CurrencyRecordServiceImpl(CurrencyRecordRepository currencyRecordRepository, CurrencyRecordClient client, ObjectMapper objectMapper) {
        this.currencyRecordRepository = currencyRecordRepository;
        this.client = client;
        this.objectMapper = objectMapper;
    }
}
