package wojtanowski.konrad.currencyapp.currencyRequest.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import wojtanowski.konrad.currencyapp.currencyRequest.client.api.CurrencyRequestClient;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyValueDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;
import wojtanowski.konrad.currencyapp.currencyRequest.repository.CurrencyRequestRepository;
import wojtanowski.konrad.currencyapp.currencyRequest.service.api.CurrencyRequestService;

import java.util.stream.Collectors;

@Primary
@Service
@Validated
public class CurrencyRequestServiceImpl implements CurrencyRequestService {
    private final CurrencyRequestRepository currencyRequestRepository;
    private final CurrencyRequestClient client;
    private final ObjectMapper objectMapper;

    @Override
    public GetCurrencyRequestsDTO getAllCurrencyRequests() {
        //currencyRequestRepository.save(new CurrencyRequest("EUR", "JAN KOWALSKI", 4.4567F));

        return new GetCurrencyRequestsDTO(currencyRequestRepository.findAll()
                .stream()
                .map(value -> new GetCurrencyRequestDTO(value.getCurrencyName(), value.getRequesterName(), value.getDate(), value.getCurrencyValue()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public GetCurrencyValueDTO postCurrencyRequest(PostCurrencyRequestDTO currencyRequest) {

        try {
            String responseString = client.getCurrencies();
            Float responseValue = findCurrencyValue(responseString, currencyRequest.currencyName());
            return new GetCurrencyValueDTO(responseValue);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("JSON processing error", ex);
        }
    }

    @Override
    public void saveCurrencyRequest(PostCurrencyRequestDTO currencyRequest, Float currencyValue) {
        currencyRequestRepository.save(new CurrencyRequest(currencyRequest.currencyName(), currencyRequest.requesterName(),currencyValue));
    }

    Float findCurrencyValue(String jsonResponse, String currencyName) throws JsonProcessingException {
        float responseValue = -1F;
        JsonNode jsonArray = objectMapper.readTree(jsonResponse);

        for (JsonNode element : jsonArray) {
            JsonNode ratesArray = element.get("rates");

            for (JsonNode rate : ratesArray) {
                if (currencyName.equals(rate.get("code").asText())) {
                    responseValue = rate.get("mid").floatValue();
                    break;
                }
            }
        }

        return responseValue;
    }

    @Autowired
    public CurrencyRequestServiceImpl(CurrencyRequestRepository currencyRequestRepository, CurrencyRequestClient client, ObjectMapper objectMapper) {
        this.currencyRequestRepository = currencyRequestRepository;
        this.client = client;
        this.objectMapper = objectMapper;
    }
}
