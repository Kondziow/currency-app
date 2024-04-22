package wojtanowski.konrad.currencyapp.currencyRequest.model.dto;

import java.util.List;

public record GetCurrencyRequestsDTO(
        List<GetCurrencyRequestDTO> currencyRequests
) {
}
