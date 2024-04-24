package wojtanowski.konrad.currencyapp.currencyRequest.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import wojtanowski.konrad.currencyapp.currencyRequest.client.api.CurrencyRequestClient;

@Service
public class CurrencyRequestClientImpl implements CurrencyRequestClient {
    private final WebClient webClient;

    public CurrencyRequestClientImpl(WebClient.Builder webClientBuilder, @Value("${nbp.api.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public String getCurrencies() {
        return webClient.get().uri("/api/exchangerates/tables/A?format=json")
                .retrieve().bodyToMono(String.class).block();
    }
}
