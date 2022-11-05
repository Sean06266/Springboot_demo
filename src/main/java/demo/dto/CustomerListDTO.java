package demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerListDTO {
    private  String dataKey;
    private List<BirthdayDTO> birthdayList;
}
