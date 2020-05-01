package cn.li.controller.admin;

import cn.li.pojo.Employee;
import cn.li.pojo.House;
import cn.li.pojo.Order;
import cn.li.pojo.OrderHouseDetail;
import cn.li.service.HouseService;
import cn.li.service.OrderHouseDetailService;
import cn.li.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房间订单详情表
 * @author Mr.Wang
 */
@Controller
@RequestMapping("/main")
public class OrderHouseDetailController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private OrderHouseDetailService orderHouseDetailService;

    @Autowired
    private OrderService orderService;

    /**
     * 打开分房页面
     * @param model
     * @return
     */
    @GetMapping("/openHouseAssignment")
    public String openHouseAssignment(Integer orderId,Integer houseTypeId, String houseTypeName,Integer reserveCount, Model model){
        List<House> houseList = houseService.getHouseList();
        Map<String,Object> house = new HashMap<>();
        house.put("houseTypeId",houseTypeId);
        house.put("houseTypeName",houseTypeName);
        house.put("reserveCount",reserveCount);
        house.put("orderId",orderId);

        model.addAttribute("houseList",houseList);
        model.addAttribute("houseOrder",house);
        return "house/houseAssignment";
    }



    /**
     * 分房方法
     * @return
     */
    @PostMapping("/updateHouseStatus")
    public @ResponseBody
    Map<String, Object> updateHouseStatus(String str, Integer orderId, HttpSession session){
        Employee employee = (Employee)session.getAttribute("employee");
        String[] strs = str.split(",");
        Map<String, Object> resultMap = orderHouseDetailService.houseAssignment(strs,orderId,employee.getId());
        return resultMap;
    }

    @RequestMapping("/openCheckOut")
    public String openCheckOut(){
        return "house/checkout";
    }
    /**
     * 将要退房的所有订单
     * @return
     */
    @RequestMapping("/getCheckOuts")
    public @ResponseBody
    Map<String,Object> getHouseCjeckOuts(@RequestParam Integer page, @RequestParam Integer limit){
        Map<String,Object> map = new HashMap<>();
        PageInfo<Order> houseOrders = orderService.getOrderCheckOuts(page, limit);
        map.put("count",orderService.selectAllCount(3));
        map.put("data",houseOrders.getList());
        map.put("msg","成功");
        map.put("code","0");
        return map;
    }

    /**
     * 退房接口
     * @return
     */
    @RequestMapping("/checkout")
    public @ResponseBody
    Map<String,Object> checkOut(Integer orderId,Integer houseTypeId){
        return  orderHouseDetailService.checkOutAndUpdateHouseStatus(orderId,houseTypeId);
    }

    @RequestMapping("/houseRenew")
    public @ResponseBody
    Map<String,Object> houseRenew(String endDate,Integer orderId,Integer day,String houseOrders){
        Map<String,Object> map = null;
        try {
            map= orderHouseDetailService.houseRenew(endDate,orderId,day,houseOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
