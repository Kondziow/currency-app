package wojtanowski.konrad.currencyapp.currencyRecord.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import wojtanowski.konrad.currencyapp.currencyRecord.client.api.CurrencyRecordClient;

@Service
public class CurrencyRecordClientImpl implements CurrencyRecordClient {
    private final WebClient webClient;

    @Autowired
    public CurrencyRecordClientImpl(WebClient.Builder webClientBuilder, @Value("${nbp.api.url}") String url) {
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    @Override
    public String getCurrencies() {
        return webClient.get().uri("/api/exchangerates/tables/A?format=json")
                .retrieve().bodyToMono(String.class).block();
    }
}
