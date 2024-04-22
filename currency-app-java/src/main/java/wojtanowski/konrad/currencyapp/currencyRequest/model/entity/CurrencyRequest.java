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
        private UUID id;

        @NotNull
        @NotBlank
        @Size(min = 3, max = 3)
        private String currencyName;

        @NotNull
        @NotBlank
        @Size(max = 100)
        private String requesterName;

        @CreationTimestamp
        @Column(updatable = false)
        private Timestamp date;

        private Float currencyValue;

        public CurrencyRequest() {
        }

        public CurrencyRequest(String currencyName, String requesterName, Float currencyValue) {
                this.currencyName = currencyName;
                this.requesterName = requesterName;
                this.currencyValue = currencyValue;
        }

        public UUID getId() {
                return id;
        }

        public String getCurrencyName() {
                return currencyName;
        }

        public String getRequesterName() {
                return requesterName;
        }

        public Timestamp getDate() {
                return date;
        }

        public Float getCurrencyValue() {
                return currencyValue;
        }
}
