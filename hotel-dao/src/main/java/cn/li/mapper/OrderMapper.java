package cn.li.mapper;

import cn.li.pojo.MemberOrder;
import cn.li.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    /**
     * 修改餐品订单的状态
     *
     * @param id
     * @return
     */
    int updateOrderPriductStatus(Integer id);

    /**
     * 根据ID删除一条订单信息
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 查询所有订单
     *
     * @return
     */
    List<Order> selectOrderList(@Param("orderCode") String orderCode, @Param("clientName") String clientName, @Param("orderStatus") Integer orderStatus);

    Integer selectAllCount(@Param("type")Integer type);

    /**
     * 查询商品订单
     *
     * @param orderCode
     * @param clientName
     * @return
     */
    List<Order> selectProductOrderList(@Param("orderCode") String orderCode);

    /**
     * 前台个人中心查询登录用户的订单
     *
     * @param clientId
     * @return
     */
    List<MemberOrder> selectOrderInfoByClientId(@Param("clientId") Integer clientId);

    /**
     * 根据当前登录用户的ID查询其所有订单总数
     *
     * @param clientId
     * @return
     */
    Integer selectCountAllByClientId(@Param("clientId") Integer clientId);

    /**
     * 根据订单ID和用户ID修改订单状态（修改为取消订单）
     *
     * @param clientId
     * @param orderCode
     * @return
     */
    Integer updateOrderStatusByClientIdAndOrderId(@Param("clientId") Integer clientId, @Param("orderCode") String orderCode);

    /**
     * 根据订单号查出订单详情中该客户入住的房间类型
     *
     * @param orderId 订单号
     * @return
     */
    Integer selectHouseTypeByOrderId(@Param("orderId") Integer orderId);

    /**
     * 获取即将退房的订单集合
     *
     * @return
     */
    List<Order> selectOrderCheckOuts();

    /**
     * 根据Id获取该订单当前订单每间房每天的价格
     * @return
     */
    Integer selectDayHouseMoenyById(Integer id);
}