import demo.controller.DemoController;
import demo.dao.CurrencyDAO;
import demo.demoApplication;
import demo.dto.CurrencyDTO;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = demoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest extends DemoTest {
    @Autowired
    DemoController demoController;

    @Autowired
    private CurrencyDAO currencyDAO;

    @Test
    @Order(2)
    public void addTest() {
        System.out.println("新增幣別測試案例");
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCurrencyEn("EUR");
        currencyDTO.setCurrencyCh("歐元");
        currencyDTO.setRate("20,732.7871");
        //currencyDAO.save(currencyDTO);
        demoController.doSaveCurrency(currencyDTO);
        List<CurrencyDTO> list = currencyDAO.findAll();
        System.out.println(list);

        CurrencyDTO currencyDTO1 = currencyDAO.findByCurrencyEn("EUR");
        assertThat(currencyDTO1).isNotNull();
        System.out.println(currencyDTO1);

    }

    @Test
    @Order(1)
    public void selectTest() {
        System.out.println("查詢幣別對應表資料測試案例");
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCurrencyEn("USD");
        currencyDTO.setCurrencyCh("美元");
        currencyDTO.setRate("20,732.7871");
        currencyDAO.save(currencyDTO);
        List<CurrencyDTO> list = demoController.doFindAllCurrency();
        System.out.println(list);
    }


    @Test
    @Order(3)
    public void updateTest() {
        System.out.println("更新測試案例");
        CurrencyDTO currencyDTO = currencyDAO.findByCurrencyEn("USD");
        assertThat(currencyDTO).isNotNull();
        currencyDTO.setCurrencyCh("美金");
        currencyDTO.setRate("20,583.3330");
        demoController.doUpdateCurrency(currencyDTO);
        List<CurrencyDTO> list = currencyDAO.findAll();
        System.out.println(list);
    }

    @Test
    @Order(4)
    public void deleteTest() {
        System.out.println("刪除測試案例");
        CurrencyDTO currencyDTO = currencyDAO.getById("USD");
        assertThat(currencyDTO).isNotNull();
        String currencyEn = currencyDTO.getCurrencyEn();
        demoController.doDeleteCurrency(currencyEn);
        Iterable<CurrencyDTO> currency = currencyDAO.findAll();
        assertThat(currency).hasSize(1);
    }

    @Test
    @Order(5)
    public void testCoindesk() {
        System.out.println("呼叫 coindesk API");
        demoController.testCoindesk();
    }

    @Test
    @Order(6)
    public void testCoindesk1() throws IOException {
        System.out.println("呼叫 coindesk API 進行資料轉換");
        demoController.testCoindesk1();
    }

    @Test
    @Order(7)
    public void doFindAllCustomer() throws IOException {
        System.out.println("呼叫 doFindAllCustomer 進行資料轉換");
        demoController.doFindAllCustomer();
    }
}
