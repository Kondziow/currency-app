package wojtanowski.konrad.currencyapp.currencyRequest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;
import wojtanowski.konrad.currencyapp.currencyRequest.repository.CurrencyRequestRepository;
import wojtanowski.konrad.currencyapp.currencyRequest.service.api.CurrencyRequestService;

import java.util.stream.Collectors;

@Primary
@Service
public class CurrencyRequestServiceImpl implements CurrencyRequestService {
    private final CurrencyRequestRepository currencyRequestRepository;


    @Override
    public GetCurrencyRequestsDTO getAllCurrencyRequests() {
        currencyRequestRepository.save(new CurrencyRequest("EUR", "JAN KOWALSKI", 4.4567F));

        return new GetCurrencyRequestsDTO(currencyRequestRepository.findAll()
                .stream()
                .map(value -> {
                    return new GetCurrencyRequestDTO(value.getCurrencyName(), value.getRequesterName(), value.getDate(), value.getCurrencyValue());
                })
                .collect(Collectors.toList())
        );
    }

    @Autowired
    public CurrencyRequestServiceImpl(CurrencyRequestRepository currencyRequestRepository) {
        this.currencyRequestRepository = currencyRequestRepository;
    }
}
