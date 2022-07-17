package demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.DemoService;
import demo.dto.CurrencyDTO;
import demo.dto.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@RestController
public class DemoController {
    @Autowired
    DemoService demoService;

    @RequestMapping("/")
    String home() throws IOException {
        testCoindesk();
        //testCoindesk1();
        return "hello";
    }

    @RequestMapping("/doSaveCurrency")
    public String doSaveCurrency(CurrencyDTO currencyDTO) {
//        currencyDTO.setCurrencyEn("USD");
//        currencyDTO.setCurrencyCh("美元");
//        currencyDTO.setRate("20,673.4560");
//        currencyDTO.setCurrencyEn("EUR");
//        currencyDTO.setCurrencyCh("歐元");
//        currencyDTO.setRate("20,196.7616");
        demoService.add(currencyDTO);
        return "新增成功";
    }

    @RequestMapping("/doUdateCurrency")
    public String doUpdateCurrency(CurrencyDTO currencyDTO) {
//        currencyDTO.setCurrencyEn("USD");
//        currencyDTO.setCurrencyCh("美金");
//        currencyDTO.setRate("20,673.6660");
        demoService.update(currencyDTO);
        return "更新成功";
    }

    @RequestMapping("/doDeleteCurrency")
    public String doDeleteCurrency(String currencyEn) {
        //currencyEn = "USD";
        demoService.delete(currencyEn);
        return "刪除成功";
    }

    @RequestMapping("/doFindAllCurrency")
    public List<CurrencyDTO> doFindAllCurrency() {
        List<CurrencyDTO> currencyDTOList = demoService.findAllCurrency();
        System.out.println(currencyDTOList.size());
        return currencyDTOList;
    }

    @RequestMapping("/doFindCurrency")
    public CurrencyDTO doFindCurrency(String currencyEn) {
        CurrencyDTO currencyDTO = demoService.findCurrency(currencyEn);
        System.out.println(currencyDTO);
        return currencyDTO;
    }


    public void testCoindesk() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        String productRes = restTemplate
                .getForObject(url, String.class);
        System.out.println(productRes);
    }

    public void testCoindesk1() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        ObjectMapper mapper = new ObjectMapper();
        String productRes = restTemplate
                .getForObject(url, String.class);
        TestDTO data = mapper.readValue(productRes, TestDTO.class);
        System.out.println(data);
        System.out.println(data.getTime().getUpdated());
        System.out.println(formatTime1(data.getTime().getUpdated()));
        System.out.println(data.getTime().getUpdatedISO());
        System.out.println(formatTime(data.getTime().getUpdatedISO()));
        System.out.println(data.getTime().getUpdateduk());
        System.out.println(formatTime2(data.getTime().getUpdateduk()));
    }


    public static String formatTime(String timeStr) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf1.parse(timeStr);
            return sdf2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String formatTime1(String timeStr) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMM d, yyyy HH:mm:ss 'UTC'", Locale.ENGLISH);
            sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf1.parse(timeStr);
            return sdf2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public static String formatTime2(String timeStr) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMM d, yyyy 'at' HH:mm 'BST'", Locale.ENGLISH);
            sdf1.setTimeZone(TimeZone.getTimeZone("BST"));
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf1.parse(timeStr);
            return sdf2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }


}
