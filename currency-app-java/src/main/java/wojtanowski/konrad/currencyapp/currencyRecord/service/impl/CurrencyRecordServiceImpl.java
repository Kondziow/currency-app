package wojtanowski.konrad.currencyapp.currencyRecord.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import wojtanowski.konrad.currencyapp.currencyRecord.client.api.CurrencyRecordClient;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;
import wojtanowski.konrad.currencyapp.currencyRecord.model.entity.CurrencyRecord;
import wojtanowski.konrad.currencyapp.currencyRecord.repository.CurrencyRecordRepository;
import wojtanowski.konrad.currencyapp.currencyRecord.service.api.CurrencyRecordService;

@Primary
@Service
@Validated
public class CurrencyRecordServiceImpl implements CurrencyRecordService {
    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final CurrencyRecordRepository currencyRecordRepository;
    private final CurrencyRecordClient client;
    private final ObjectMapper objectMapper;

    @Override
    public Page<GetCurrencyRecordResponse> getAllCurrencyRecords(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<CurrencyRecord> currencyRecordPage = currencyRecordRepository.findAll(pageRequest);

        return currencyRecordPage
                .map(value -> new GetCurrencyRecordResponse(
                        value.getCurrencyName(),
                        value.getRequesterName(),
                        value.getDate(),
                        value.getCurrencyValue()));

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

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    @Autowired
    public CurrencyRecordServiceImpl(CurrencyRecordRepository currencyRecordRepository, CurrencyRecordClient client, ObjectMapper objectMapper) {
        this.currencyRecordRepository = currencyRecordRepository;
        this.client = client;
        this.objectMapper = objectMapper;
    }
}
