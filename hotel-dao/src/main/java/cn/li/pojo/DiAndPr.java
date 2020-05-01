package cn.li.pojo;

import lombok.Data;

/**
 * 购物车实体类
 */
@Data
public class DiAndPr {
    private Integer id;//餐品ID
    private String name;//餐品名
    private Integer price;//餐品价格
    private String picture;//餐品图片
    private Integer type;//餐品类型(0:菜品,1:商品)
    private Integer count;//餐品数量
}
