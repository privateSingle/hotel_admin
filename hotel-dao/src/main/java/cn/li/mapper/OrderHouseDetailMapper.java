package cn.li.mapper;

import cn.li.pojo.OrderHouseDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderHouseDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderHouseDetail record);

    int insertSelective(OrderHouseDetail record);

    OrderHouseDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderHouseDetail record);

    int updateByPrimaryKey(OrderHouseDetail record);

    List<OrderHouseDetail> selectByOrderEndDate();

    Integer getAllHouseOrderCount();


    /**
     * 查询客房订单详情集合  根据订单iD
     * @param orderId
     * @return
     */
    List<OrderHouseDetail> selectOrderHouseByOrderId(@Param("orderId")Integer orderId);
}