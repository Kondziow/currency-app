package wojtanowski.konrad.currencyapp.currencyRecord.model.dto;

import java.sql.Timestamp;

public record GetCurrencyRecordResponse(
        String currencyName,
        String requesterName,
        Timestamp date,
        Float currencyValue
) {
}
