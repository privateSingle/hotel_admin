package cn.li.controller.admin;

import cn.li.service.HouseTypeService;
import cn.li.service.OrderHouseDetailService;
import cn.li.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: 订单表控制器
 */
@Controller
@RequestMapping("/main")
public class OrderController {

    /**
     * 客房订单页面挑战
     */
   @Autowired
   private OrderService orderService;

   @Autowired
   private HouseTypeService houseTypeService;

   @Autowired
   private OrderHouseDetailService orderHouseDetailService;

    /**
     * 客房订单页面跳转
     */
   @RequestMapping("/openOrder")
   public String openOrder(){
       return "house/orderList";
   }

    /**
     * 餐品订单页面挑战
     */
   @RequestMapping("/openProductOrder")
   public String openProductOrder(){
       return "house/productOrderList";
   }

   /**
    * 查询所有客房订单
    * @param page
    * @param limit
    * @return
    */
   @GetMapping("/getOrderList")
   public @ResponseBody
   Map<String, Object> getList(@RequestParam Integer page,@RequestParam Integer limit,
                               @RequestParam(required = false) String orderCode,
                               @RequestParam(required = false) String clientName,
                               @RequestParam(required = false) Integer orderStatus)
   {
       Map<String, Object> mapResult = new HashMap<>();
       try {
           PageInfo pageInfo  = orderService.getOrders(page,limit,orderCode,clientName,orderStatus);
            mapResult.put("count",orderService.selectAllCount(0));
            mapResult.put("data",pageInfo.getList());
            mapResult.put("msg","成功");
            mapResult.put("code","0");
       }catch (Exception e){
         e.printStackTrace();
       }
       return mapResult;
   }
}
