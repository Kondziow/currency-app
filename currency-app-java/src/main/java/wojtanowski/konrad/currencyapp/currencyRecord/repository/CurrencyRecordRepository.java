package wojtanowski.konrad.currencyapp.currencyRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wojtanowski.konrad.currencyapp.currencyRecord.model.entity.CurrencyRecord;

import java.util.UUID;

@Repository
public interface CurrencyRecordRepository extends JpaRepository<CurrencyRecord, UUID> {
}
