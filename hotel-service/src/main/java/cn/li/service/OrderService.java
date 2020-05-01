package cn.li.service;

import cn.li.pojo.MemberOrder;
import cn.li.pojo.OrderHouseDetail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.pojo.Order;
import cn.li.mapper.OrderMapper;

import java.util.List;

@Service
public class OrderService{

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 根据ID删除一条订单信息
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Order record) {
        return orderMapper.insert(record);
    }

    
    public int insertSelective(Order record) {
        return orderMapper.insertSelective(record);
    }

    
    public Order selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Order record) {
        return orderMapper.updateByPrimaryKey(record);
    }

    /**
     * 客房订单
     * @param page
     * @param limit
     * @param orderCode
     * @param clientName
     * @return
     */
    public PageInfo getOrders(Integer page, Integer limit, String orderCode, String clientName,Integer orderStatus) {
        PageHelper.startPage(page,limit);
        try {
            return new PageInfo<Order>(orderMapper.selectOrderList(orderCode,clientName,orderStatus));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 商品订单
     * @param page
     * @param limit
     * @param orderCode
     * @param clientName
     * @return
     */
    public PageInfo getProductOrders(Integer page, Integer limit, String orderCode) {
        PageHelper.startPage(page,limit);
        try {
            return new PageInfo<Order>(orderMapper.selectProductOrderList(orderCode));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取订单数量
     * @return
     */
    public Integer selectAllCount(Integer type) {
        return orderMapper.selectAllCount(type);
    }

    /**
     * 查询全部订单信息
     * @param page
     * @param limit
     * @param clientId
     * @return
     */
    public List<MemberOrder> selectAllInfo(Integer page, Integer limit, Integer clientId) {
        PageHelper.startPage(page, limit);
        return new PageInfo<MemberOrder>(orderMapper.selectOrderInfoByClientId(clientId)).getList();
    }

    /**
     * 根据当前登录用户的ID查询其所有订单总数
     * @param clientId
     * @return
     */
    public Integer selectCountAllByClientId(Integer clientId) {
        return orderMapper.selectCountAllByClientId(clientId);
    }

    /**
     * 根据订单ID和用户ID修改订单状态（修改为取消订单）
     * @param clientId
     * @param orderCode
     * @return
     */
    public Integer updateOrderStatusByClientIdAndOrderId(Integer clientId, String orderCode){
        return orderMapper.updateOrderStatusByClientIdAndOrderId(clientId, orderCode);
    }

    /**
     * 根据订单号查出订单详情中该客户入住的房间类型
     * @param orderId 订单号
     * @return
     */
    public Integer selectHouseTypeByOrderId(Integer orderId) {
        return orderMapper.selectHouseTypeByOrderId(orderId);
    }


    /**
     * 获取退房的订单集合
     */
    public PageInfo<Order> getOrderCheckOuts(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        return new PageInfo<>(orderMapper.selectOrderCheckOuts());
    }
}
