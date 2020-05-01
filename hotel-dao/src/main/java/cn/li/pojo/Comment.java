package cn.li.pojo;

import java.util.Date;
import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Comment {
    /**
    * 评价ID
    */
    private Integer id;

    /**
    * 评价内容
    */
    private String content;

    /**
    *  评价的图片
    */
    private String picture;

    /**
    * 评价人(客户ID)
    */
    private Integer clientId;

    /**
    * 房间类型ID
    */
    private Integer houseTypeId;

    /**
    * 评价时间
    */
    private String date;

    /**
    * 评价登记(1-5)
    */
    private Integer level;

     /**
      * 房间类型
      */
    private HouseType houseType;

     /**
      * 客人名字
      */
    private String clientName;
}