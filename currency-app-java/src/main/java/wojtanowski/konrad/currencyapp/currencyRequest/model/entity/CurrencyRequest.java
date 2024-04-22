package wojtanowski.konrad.currencyapp.currencyRequest.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class CurrencyRequest {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(updatable = false, nullable = false)
        UUID id;

        @NotNull
        @NotBlank
        @Size(min = 3, max = 3)
        String currencyName;

        @NotNull
        @NotBlank
        @Size(max = 100)
        String requesterName;

        @CreationTimestamp
        @Column(updatable = false)
        Timestamp date;

        Integer currencyValue;

        public CurrencyRequest() {
        }

        public CurrencyRequest(String currencyName, String requesterName, Integer currencyValue) {
                this.currencyName = currencyName;
                this.requesterName = requesterName;
                this.currencyValue = currencyValue;
        }
}
