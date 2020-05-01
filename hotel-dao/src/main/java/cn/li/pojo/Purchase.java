package cn.li.pojo;

import java.util.Date;
import java.util.List;

import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Purchase {
    /**
    * 采购ID
    */
    private Integer id;

    /**
    * 商品ID
    */
    private Integer productId;

    /**
    * 价格
    */
    private Integer price;

    /**
    * 数量
    */
    private Integer count;

     /**
      * 采购之前的数量
      */
    private Integer beforeCount;

    /**
    * 采购日期
    */
    private String purchaseDate;

    /**
    * 单位
    */
    private String unit;

     /**
      * 采购总价
      */
    private Integer totalPrice;

     /**
      * 商品类型集合
      */
    private Product product;
}