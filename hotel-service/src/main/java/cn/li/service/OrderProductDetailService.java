package cn.li.service;

import cn.li.mapper.BusinessMapper;
import cn.li.mapper.OrderMapper;
import cn.li.pojo.Business;
import cn.li.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.pojo.OrderProductDetail;
import cn.li.mapper.OrderProductDetailMapper;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderProductDetailService{

    @Autowired
    private OrderProductDetailMapper orderProductDetailMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private OrderMapper orderMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return orderProductDetailMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(OrderProductDetail record) {
        return orderProductDetailMapper.insert(record);
    }

    
    public int insertSelective(OrderProductDetail record) {
        return orderProductDetailMapper.insertSelective(record);
    }

    
    public OrderProductDetail selectByPrimaryKey(Integer id) {
        return orderProductDetailMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(OrderProductDetail record) {
        return orderProductDetailMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(OrderProductDetail record) {
        return orderProductDetailMapper.updateByPrimaryKey(record);
    }

    /**
     * 修改订单状态
     * @param id
     * @return
     */
    @Transactional
    public Map<String, Object> updateProductOrderStatus(Integer id,Integer empId){
        Map<String, Object> mapResult = new HashMap<>();
        try {
            int i = orderMapper.updateOrderPriductStatus(id);
            Order order = orderMapper.selectByPrimaryKey(id);
            if (i>0){
                //添加营业报表
                Business business = new Business();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //订单时间
                business.setSellDate(simpleDateFormat.format(new Date()));
                //销售种类
                business.setSellTypeId(2);
                //价格
                business.setPrice(order.getMoney());
                business.setEmpId(empId);
                businessMapper.insertSelective(business);

                mapResult.put("result","success");
                mapResult.put("msg","送餐成功！");
            }else {
                mapResult.put("result","error");
                mapResult.put("msg","送餐失败！");
            }
        }catch (Exception e){
            mapResult.put("result","error");
            mapResult.put("msg","送餐出现异常！");
            e.printStackTrace();
        }
        return mapResult;
    }

    public List<OrderProductDetail> selectOrderProductByOrderId(Integer orderId) {
        return orderProductDetailMapper.selectOrderProductByOrderId(orderId);
    }
}
