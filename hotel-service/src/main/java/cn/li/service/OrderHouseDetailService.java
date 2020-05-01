package cn.li.service;

import cn.li.mapper.*;
import cn.li.pojo.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderHouseDetailService{

    @Autowired
    private OrderHouseDetailMapper orderHouseDetailMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BusinessMapper businessMapper;

    //订单详情数据访问层
    @Autowired
    private OrderMapper orderMapper;

    //客房类型
    @Autowired
    private HouseTypeMapper houseTypeMapper;

    //用户数据
    @Autowired
    private ClientMapper clientMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return orderHouseDetailMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(OrderHouseDetail record) {
        return orderHouseDetailMapper.insert(record);
    }

    
    public int insertSelective(OrderHouseDetail record) {
        return orderHouseDetailMapper.insertSelective(record);
    }

    
    public OrderHouseDetail selectByPrimaryKey(Integer id) {
        return orderHouseDetailMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(OrderHouseDetail record) {
        return orderHouseDetailMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(OrderHouseDetail record) {
        return orderHouseDetailMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询客房订单集合
     * @return
     */
    public PageInfo<OrderHouseDetail> getHouseOrders(Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        return new PageInfo<>(orderHouseDetailMapper.selectByOrderEndDate());
    }

    public Integer getAllHouseOrderCount() {
        return orderHouseDetailMapper.getAllHouseOrderCount();
    }

    /**
     * 预定房间
     * @param order
     * @param orderHouseDetail
     * @return
     */
    @Transactional
    public  Map<String, Object> houseReserve(Order order,OrderHouseDetail orderHouseDetail){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //创建一条订单详情
            int orderReturn = orderMapper.insert(order);
            //创建客房订单
            orderHouseDetail.setOrderId(order.getId());//赋值订单详情id
            for (Integer i = 0; i < order.getReserverCount(); i++) {
                int a = orderHouseDetailMapper.insert(orderHouseDetail);
                if (a<0) throw new Exception();
            }
            //订单结算
            Integer settlement = clientMapper.orderSettlement(order.getClientId(),order.getMoney());
            //修改房间数量
            int updateRemain = houseTypeMapper.updateHouseRemain(orderHouseDetail.getHouseTypeId(),order.getReserverCount());
            if (orderReturn>0 && settlement>0 && updateRemain>0){
                resultMap.put("result","success");
                resultMap.put("msg", "订单创建成功！");
            }else {
                resultMap.put("result", "error");
                resultMap.put("msg", "订单创建失败！");
            }
        }catch (Exception e){
            resultMap.put("result", "error");
            resultMap.put("msg", "订单创建时出现异常！");
            e.printStackTrace();
        }
        return  resultMap;
    }

    /**
     * 分房
     * @return
     */
    public Map<String, Object> houseAssignment(String[] strs, Integer orderId,Integer empId){
        Map<String, Object> resultMap = new HashMap<>();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        List<OrderHouseDetail> houseDetailList = orderHouseDetailMapper.selectOrderHouseByOrderId(orderId);

        try {
            for (int i = 0; i < strs.length; i++) {
                //修改客房状态,已入住
                House house = new House();
                house.setHouseCode(strs[i]);
                house.setHouseStatus(1);
                houseMapper.updateHouseStatus(house);


                //为客房订单分配房间
                OrderHouseDetail houseDetail = houseDetailList.get(i);
                houseDetail.setHouseCode(strs[i]);
                orderHouseDetailMapper.updateByPrimaryKeySelective(houseDetail);
            }

            //修改订单状态
            order.setOrderStatus(3);
            int i = orderMapper.updateByPrimaryKeySelective(order);
            //查询修改的客房订单

            if (i>0){
                //添加营业报表
                Business business = new Business();
                //订单时间
                business.setSellDate(order.getCreateDate());
                //销售种类
                business.setSellTypeId(1);
                //员工
                business.setEmpId(empId);
                //价格
                business.setPrice(order.getMoney());
                //添加营业报表
                businessMapper.insertSelective(business);
                resultMap.put("result","success");
                resultMap.put("msg", "分房成功！");
            }else {
                resultMap.put("result", "error");
                resultMap.put("msg", "分房失败！");
            }
        }catch (Exception e){
            resultMap.put("result", "error");
            resultMap.put("msg", "分房出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 退房方法
     * @param orderId
     * @param houseTypeId
     * @return
     */
    public Map<String, Object> checkOutAndUpdateHouseStatus(Integer orderId,Integer houseTypeId){
        Map<String,Object> map = new HashMap<>();
        try {
            List<OrderHouseDetail> houseDetails = orderHouseDetailMapper.selectOrderHouseByOrderId(orderId);
            //退房
            Order order = new Order();
            order.setId(orderId);
            order.setOrderStatus(1);
            int update = orderMapper.updateByPrimaryKeySelective(order);
            //修改房间状态
            for (OrderHouseDetail houseDetail:houseDetails) {
                House house =new House();
                house.setHouseCode(houseDetail.getHouseCode());
                house.setHouseStatus(0);
                int i = houseMapper.updateHouseStatus(house);
                if (i<0) throw  new Exception();
            }
            //修改客房数量
            int i = houseTypeMapper.updateHouseTypeRemain(houseTypeId,1);
            if (i>0 && update>0){
                map.put("result","success");
                map.put("msg","退房成功！");
            }else {
                map.put("result","error");
                map.put("msg","退房失败！");
            }
        }catch (Exception e){
            map.put("result","error");
            map.put("msg","退房出现异常");
        }
        return map;
    }

    /**
     * 续房方法
     * @param endDate 续房后的离店时间
     * @param orderId 订单的id
     * @param day 续房的天数
     * @param houseOrders 订单详情的集合
     * @return
     * @throws Exception
     */
    public Map<String, Object> houseRenew(String endDate,Integer orderId,Integer day,String houseOrders){
        System.out.println("endDate:"+endDate+"-----orderId:"+orderId+"------day:"+day);
        Map<String,Object> map = new HashMap<>();
        //获取每个房间每天的价格
        Integer dayMoney = orderMapper.selectDayHouseMoenyById(orderId);
        Order order = orderMapper.selectByPrimaryKey(orderId);//获取订单
        Integer money = day*dayMoney*order.getReserverCount();//续房总金额
        Client client = clientMapper.selectByPrimaryKey(order.getClientId());//获取用户
        int clientUpdate = 0;
        if (client.getBalance()<money){
            map.put("result","error");
            map.put("msg","用户金额不足");
            return map;
        }else {
            client.setBalance(client.getBalance()-money);
            clientUpdate = clientMapper.updateByPrimaryKeySelective(client);
        }

            //获取订单详情的数据
        String  param = null;
        try {
            param = URLDecoder.decode(houseOrders,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONArray array = JSONArray.parseArray(param);
        List<OrderHouseDetail> houseDetails = JSONObject.parseArray(array.toJSONString(),OrderHouseDetail.class);
            //修改离店时间和价格

            Integer renewMoney = 0;//总金额
            for (OrderHouseDetail houseDetail:houseDetails) {
                //修改订单详情的价格
                houseDetail.setMoney(houseDetail.getMoney()+(dayMoney*day));
                //修改离店时间
                houseDetail.setEndDate(endDate);
                //获取订单总金额
                renewMoney=renewMoney+houseDetail.getMoney();
                //修改
                int i = orderHouseDetailMapper.updateByPrimaryKeySelective(houseDetail);
                //没有修改抛出异常
                if (i<0){
                    map.put("result","error");
                    map.put("msg","续房出现异常");
                    return map;
                }
            }

            //修改订单总金额
            order.setMoney(renewMoney);
            int i = orderMapper.updateByPrimaryKeySelective(order);

            if (i>0&&clientUpdate>0){
                map.put("result","success");
                map.put("msg","续房成功！");
            }
        return map;
    }


}
