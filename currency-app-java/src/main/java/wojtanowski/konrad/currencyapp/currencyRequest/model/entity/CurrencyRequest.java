package wojtanowski.konrad.currencyapp.currencyRequest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

public record CurrencyRequest(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(updatable = false, nullable = false)
        UUID id,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 3)
        String currency,

        @NotNull
        @NotBlank
        @Size(max = 100)
        String name,

        @CreationTimestamp
        @Column(updatable = false)
        Timestamp date,

        Integer value
) {}
