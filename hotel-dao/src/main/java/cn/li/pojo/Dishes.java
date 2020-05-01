package cn.li.pojo;

import lombok.Data;

@Data
public class Dishes {
    /**
    * 菜品表ID主键
    */
    private Integer id;

    /**
    * 菜品名
    */
    private String name;

    /**
    * 菜品价格
    */
    private Integer price;

    /**
    * 菜品图片
    */
    private String picture;
}