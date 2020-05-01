package cn.li.pojo;

import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Product {
    /**
    * 商品ID
    */
    private Integer id;

    /**
    * 商品名
    */
    private String name;

    /**
    * 商品总量
    */
    private Integer count;

     /**
      * 商品单价
      */
    private Integer price;

    /**
    * 单位
    */
    private String unit;

    /**
    * 商品类型
    */
    private Integer productType;

     /**
      * 商品状态(0:可销售;1:非销售)
      */
    private Integer status;

     /**
      * 商品图片
      */
    private String productPicture;

     /**
      * 商品类型对象
      */
    private ProductType productTypeBean;
}