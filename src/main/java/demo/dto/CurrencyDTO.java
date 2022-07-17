package demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency")
@NoArgsConstructor
public class CurrencyDTO {
    @Id
    @Column(name = "currency_en", nullable = false, length = 20)
    private String currencyEn;
    @Column(name = "currency_ch", nullable = false, length = 30)
    private String currencyCh;
    @Column(name = "rate", nullable = false, length = 20)
    private String rate;

    public CurrencyDTO(String currencyEn, String currencyCh, String rate) {
    }
}
