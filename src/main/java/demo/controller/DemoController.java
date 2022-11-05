package demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import demo.DemoService;
import demo.dto.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class DemoController {
    @Autowired
    DemoService demoService;

    @RequestMapping("/")
    String home() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        testCoindesk();
        //testCoindesk1();
        String input = "baeldung";

        SecretKey key = generateKey(128);

        IvParameterSpec ivParameterSpec = generateIv();

        String algorithm = "AES/CBC/PKCS5Padding";

        String cipherText = encrypt(algorithm, input, key, ivParameterSpec);

        String plainText = decrypt(algorithm, cipherText, key, ivParameterSpec);
        System.out.println(input);
        System.out.println(cipherText);
        System.out.println(plainText);
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


    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        keyGenerator.init(n);

        SecretKey key = keyGenerator.generateKey();

        return key;

    }

    public static IvParameterSpec generateIv() {

        byte[] iv = new byte[16];

        new SecureRandom().nextBytes(iv);

        return new IvParameterSpec(iv);

    }


    public static String encrypt(String algorithm, String input, SecretKey key,

                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,

            InvalidAlgorithmParameterException, InvalidKeyException,

            BadPaddingException, IllegalBlockSizeException {


        Cipher cipher = Cipher.getInstance(algorithm);

        cipher.init(Cipher.ENCRYPT_MODE, key,iv);

        byte[] cipherText = cipher.doFinal(input.getBytes());

        return Base64.getEncoder()

                .encodeToString(cipherText);

    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,

                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,

            InvalidAlgorithmParameterException, InvalidKeyException,

            BadPaddingException, IllegalBlockSizeException {


        Cipher cipher = Cipher.getInstance(algorithm);

        cipher.init(Cipher.DECRYPT_MODE, key,iv);

        byte[] plainText = cipher.doFinal(Base64.getDecoder()

                .decode(cipherText));

        return new String(plainText);

    }

    @RequestMapping("/doFindAllCustomer")
    public List<CustomerListDTO> doFindAllCustomer() {
        JSONObject mark = new JSONObject();
        JSONObject jo = new JSONObject();
        jo.put("birthday", "2020-02-28");
        jo.put("id", "111111");
        jo.put("phone", "0985269856");
        jo.put("age", 20);
        jo.put("name", "John");
        jo.put("sex", "1");
        jo.put("mark",mark);
        jo.put("jj_cc", "1");
        JSONObject jo1 = new JSONObject();
        jo1.put("birthday", "1995-02-28");
        jo1.put("id", "111111");
        jo1.put("phone", "0985269856");
        jo1.put("age", 40);
        jo1.put("name", "Sean");
        jo1.put("sex", "2");
        JSONObject jo2 = new JSONObject();
        jo2.put("birthday", "1995-10-10");
        jo2.put("id", "36963");
        jo2.put("phone", "0985269856");
        jo2.put("age", 40);
        jo2.put("name", "Sean002");
        jo2.put("sex", "2");
        JSONArray ja = new JSONArray();
        ja.put(jo);
        ja.put(jo1);
        ja.put(jo2);
        List<CustomerDTO> CustomerListTemp1 = new ArrayList<>();
        if(ja.length() != 0){
            for(int i=0;i<ja.length();i++){
                Object a=ja.get(i);
                System.out.println(a);
                CustomerDTO customerDTO;
                Gson gson = new Gson();
//                String jsonInString = gson.toJson(a);
                customerDTO=gson.fromJson(a.toString(), CustomerDTO.class);
//                System.out.println(customerDTO);
                CustomerListTemp1.add(customerDTO);
            }
        }
        for(int i=0;i<CustomerListTemp1.size();i++){
            System.out.println(CustomerListTemp1.get(i));
        }

//        List<CustomerDTO> CustomerListTemp = new ArrayList<>();
//        CustomerDTO customerDTO=new CustomerDTO();
//        customerDTO.setAGE(20);
//        customerDTO.setBIRTHDAY("2020-02-28");
//        customerDTO.setID("123456789");
//        customerDTO.setPHONE("0958595858");
//        customerDTO.setNAME("Sean");
//        customerDTO.setSEX("1");
//        CustomerListTemp.add(customerDTO);
//        CustomerDTO customerDTO1=new CustomerDTO();
//        customerDTO1.setAGE(40);
//        customerDTO1.setBIRTHDAY("1995-02-28");
//        customerDTO1.setID("9585858");
//        customerDTO1.setPHONE("09582658858");
//        customerDTO1.setNAME("Sean123");
//        customerDTO1.setSEX("2");
//        CustomerListTemp.add(customerDTO1);
//        CustomerDTO customerDTO2=new CustomerDTO();
//        customerDTO2.setAGE(40);
//        customerDTO2.setBIRTHDAY("1992-10-10");
//        customerDTO2.setID("95852228");
//        customerDTO2.setPHONE("095822228858");
//        customerDTO2.setNAME("Sean456");
//        customerDTO2.setSEX("2");
//        CustomerListTemp.add(customerDTO2);
//        CustomerDTO customerDTO3=new CustomerDTO();
//        customerDTO3.setAGE(40);
//        customerDTO3.setBIRTHDAY("1965-10-10");
//        customerDTO3.setID("95852228");
//        customerDTO3.setPHONE("0958222228858");
//        customerDTO3.setNAME("Sean789");
//        customerDTO3.setSEX("2");
//        CustomerListTemp.add(customerDTO3);
//        for(int i=0;i<CustomerListTemp.size();i++){
//            System.out.println(CustomerListTemp.get(i));
//        }
        List<String> birthday=new ArrayList<>();
        List<CustomerListDTO> CustomerList = new ArrayList<>();
        List<CustomerListDTO> outList;
        for(int i=0;i<CustomerListTemp1.size();i++){
            String birth=CustomerListTemp1.get(i).getBirthday().substring(5).replace("-","/");
            if(birthday.contains(birth)){
                Integer index=birthday.indexOf(birth);
                BirthdayDTO birthdayDTO=new BirthdayDTO();
                birthdayDTO.setBirthday(CustomerListTemp1.get(i).getBirthday());
                birthdayDTO.setAge(CustomerListTemp1.get(i).getAge());
                birthdayDTO.setName(CustomerListTemp1.get(i).getName());
                birthdayDTO.setSex(CustomerListTemp1.get(i).getSex());
                birthdayDTO.setId(CustomerListTemp1.get(i).getId());
                CustomerList.get(index).getBirthdayList().add(birthdayDTO);
            }else{
                CustomerListDTO customerListDTO=new CustomerListDTO();
                customerListDTO.setDataKey(birth);
                BirthdayDTO birthdayDTO=new BirthdayDTO();
                birthdayDTO.setBirthday(CustomerListTemp1.get(i).getBirthday());
                birthdayDTO.setAge(CustomerListTemp1.get(i).getAge());
                birthdayDTO.setName(CustomerListTemp1.get(i).getName());
                birthdayDTO.setSex(CustomerListTemp1.get(i).getSex());
                birthdayDTO.setId(CustomerListTemp1.get(i).getId());
                List<BirthdayDTO> birthdayDTOList=new ArrayList<>();
                birthdayDTOList.add(birthdayDTO);
                customerListDTO.setBirthdayList(birthdayDTOList);
                CustomerList.add(customerListDTO);
                birthday.add(birth);
            }
        }
        outList=CustomerList;
        System.out.println(CustomerList.size());
        for(int i=0;i<CustomerList.size();i++){
            System.out.println(CustomerList.get(i));
        }

        return outList;
    }
}
