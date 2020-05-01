package cn.li.mapper;

import cn.li.pojo.OrderProductDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderProductDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderProductDetail record);

    int insertSelective(OrderProductDetail record);

    OrderProductDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderProductDetail record);

    int updateByPrimaryKey(OrderProductDetail record);

    /**
     * 根据订单id 查询商品订单的集合
     * @param orderId
     * @return
     */
    List<OrderProductDetail> selectOrderProductByOrderId(@Param("orderId")Integer orderId);
}