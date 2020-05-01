package cn.li.pojo;

import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class House {
    /**
    * 房间号
    */
    private String houseCode;

    /**
    * 客房类型ID
    */
    private Integer typeId;

    /**
    * 客房状态(0:未入住;1:已入住;2:已预订;3:将退房)
    */
    private Integer houseStatus;

     /**
      * 客房类型对象
      */
    private HouseType houseType;
}