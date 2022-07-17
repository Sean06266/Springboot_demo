package demo.dto;

import lombok.Data;

@Data
public class TestDTO {
  private TimeDTO time;
  private String disclaimer;
  private String chartName;
  private Object bpi;

}
