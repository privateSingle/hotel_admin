package cn.li.pojo;

import lombok.Data;

@Data
public class BuyCar {
  private Integer id;
  private String name;//餐品名
  private Integer price;//餐品价格
  private Integer proId;//餐品ID
  private Integer proType;//餐品类型ID
  private Integer count;//餐品数量
  private Integer clientId;//用户ID
}
