package cn.li.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class OrderHouseDetail {
    /**
    * 自增
    */
    private Integer id;

    /**
    * 对应一条订单的ID
    */
    private Integer orderId;

    /**
    * 预订房间类型的ID
    */
    private Integer houseTypeId;
    /**
    * 房间号
    */
    private String houseCode;

    /**
    * 价格
    */
    private Integer money;

    /**
    * 入住时间
    */
    private String startDate;

    /**
    * 退房时间
    */
    private String endDate;

    private HouseType houseType;

    private String houseTypeName;
}