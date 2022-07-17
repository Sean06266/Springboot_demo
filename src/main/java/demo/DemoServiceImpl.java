package demo;

import demo.dao.CurrencyDAO;
import demo.dto.CurrencyDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceImpl implements  DemoService{

    private CurrencyDAO currencyDAO;

    public DemoServiceImpl(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    @Override
    public void add(CurrencyDTO currencyDTO) {
        //currencyDAO.insert(currencyDTO.getCurrencyEn(),currencyDTO.getCurrencyCh(),currencyDTO.getRate());
        currencyDAO.save(currencyDTO);
    }

    @Override
    public int update(CurrencyDTO currencyDTO) {
        CurrencyDTO currencyDTO1;
        currencyDTO1 =currencyDAO.findByCurrencyEn(currencyDTO.getCurrencyEn());
        currencyDTO1.setCurrencyCh(currencyDTO.getCurrencyCh());
        currencyDTO1.setRate(currencyDTO.getRate());
        currencyDAO.save(currencyDTO1);
        return 0;
    }

    @Override
    public int delete(String currencyEn) {
        currencyDAO.deleteById(currencyEn);
        return 0;
    }

    @Override
    public List<CurrencyDTO> findAllCurrency() {
        List<CurrencyDTO> currencyDTOList =new ArrayList<>();
        currencyDTOList =currencyDAO.findAll();
        return currencyDTOList;
    }

    @Override
    public CurrencyDTO findCurrency(String currencyEn) {
        CurrencyDTO currencyDTO=currencyDAO.getById(currencyEn);
        return currencyDTO;
    }
}
