package wojtanowski.konrad.currencyapp.currencyRequest.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCurrencyRequestDTO(
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
