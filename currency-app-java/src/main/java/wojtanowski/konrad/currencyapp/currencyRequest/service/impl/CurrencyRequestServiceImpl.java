package wojtanowski.konrad.currencyapp.currencyRequest.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;
import wojtanowski.konrad.currencyapp.currencyRequest.repository.CurrencyRequestRepository;
import wojtanowski.konrad.currencyapp.currencyRequest.service.api.CurrencyRequestService;

import java.util.stream.Collectors;

@Primary
@Service
public class CurrencyRequestServiceImpl implements CurrencyRequestService {
    private final CurrencyRequestRepository currencyRequestRepository;
    private final RestTemplate restTemplate;
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
    public Float postCurrencyRequest(String currencyName, PostCurrencyRequestDTO currencyRequest) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/exchangerates/tables/A?format=json", String.class);
        String response = responseEntity.getBody();

        float responseValue = findCurrencyValue(response, currencyName);

        currencyRequestRepository.save(new CurrencyRequest(currencyRequest.currencyName(), currencyRequest.requesterName(), responseValue));

        return responseValue;
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
    public CurrencyRequestServiceImpl(CurrencyRequestRepository currencyRequestRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.currencyRequestRepository = currencyRequestRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
}
