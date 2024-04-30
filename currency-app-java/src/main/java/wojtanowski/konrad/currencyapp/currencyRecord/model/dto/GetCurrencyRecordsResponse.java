package wojtanowski.konrad.currencyapp.currencyRecord.model.dto;

import java.util.List;

public record GetCurrencyRecordsResponse(
        List<GetCurrencyRecordResponse> currencyRecords
) {
}
