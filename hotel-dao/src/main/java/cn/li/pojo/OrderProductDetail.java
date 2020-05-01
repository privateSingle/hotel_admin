package cn.li.pojo;

import lombok.Data;

@Data
public class OrderProductDetail {
    /**
    * 自增列
    */
    private Integer id;

    /**
    * 对应一条订单的id
    */
    private Integer orderId;

    /**
    * 对应一条商品信息
    */
    private Integer productId;

    /**
    * 预订数量
    */
    private Integer count;

    /**
    * 总价格
    */
    private Integer money;

    /**
     * 客房号
     */
    private String houseCode;

    private Product product;

    private Integer proType;

    private String productName;
}