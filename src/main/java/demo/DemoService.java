package demo;

import demo.dto.CurrencyDTO;

import java.util.List;

public interface  DemoService {

    //新增幣別資訊
    void add(CurrencyDTO currencyDTO);

    //修改幣別資訊
    int update (CurrencyDTO currencyDTO);

    //刪除幣別資訊
    int delete (String currencyEn);

    //取得全部幣別資訊
    List<CurrencyDTO> findAllCurrency();

    CurrencyDTO findCurrency(String currencyEn);
}
