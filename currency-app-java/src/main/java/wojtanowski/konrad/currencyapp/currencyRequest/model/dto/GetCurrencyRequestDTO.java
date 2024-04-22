package wojtanowski.konrad.currencyapp.currencyRequest.model.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record GetCurrencyRequestDTO(
        String currencyName,
        String requesterName,
        Timestamp date,
        Float currencyValue
) {
}
