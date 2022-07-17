package demo.dao;

import demo.dto.CurrencyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface CurrencyDAO
        extends JpaRepository<CurrencyDTO, String>, JpaSpecificationExecutor<CurrencyDTO> {

    CurrencyDTO findByCurrencyEn(String currencyEn);

    @Transactional
    @Modifying
    @Query(value = "insert into  currency(" +
            "currency_en, currency_ch, rate) " +
            "values (:currencyEn, :currencyCh, :rate)", nativeQuery = true)
    void insert(@Param("currencyEn") String currencyEn, @Param("currencyCh") String currencyCh, @Param("rate") String rate);
}
