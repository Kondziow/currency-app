package wojtanowski.konrad.currencyapp.currencyRecord.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCurrencyRecordRequest(
        @NotNull
        @NotBlank
        @Size(min = 3, max = 3)
        String currencyName,

        @NotNull
        @NotBlank
        @Size(max = 100)
        String requesterName
) {
}
