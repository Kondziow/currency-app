package wojtanowski.konrad.currencyapp.currencyRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wojtanowski.konrad.currencyapp.currencyRequest.model.entity.CurrencyRequest;

import java.util.UUID;

@Repository
public interface CurrencyRequestRepository extends JpaRepository<CurrencyRequest, UUID> {
}
