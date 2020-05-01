package cn.li.pojo;

import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Business {
    /**
    * 营业日报表ID
    */
    private Integer id;

    /**
    *  金额
    */
    private Integer price;

    /**
    * 销售日期
    */
    private String sellDate;

    /**
    * 销售类型ID
    */
    private Integer sellTypeId;

    /**
    * 员工ID
    */
    private Integer empId;

    /**
    *  商品ID
    */
    private String productId;

    /**
    * 采购表ID
    */
    private Integer purchaseId;

     /**
      * 销售类型实体
      */
    private SellType sellTypeBean;

     /**
      * 员工实体
      */
    private Employee employeeBean;

     /**
      * 商品实体
      */
    private Product productBean;

     /**
      * 采购实体
      */
    private Purchase purchaseBean;
}