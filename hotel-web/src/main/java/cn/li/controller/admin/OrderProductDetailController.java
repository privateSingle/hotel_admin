package cn.li.controller.admin;

import cn.li.pojo.Employee;
import cn.li.pojo.OrderProductDetail;
import cn.li.service.OrderProductDetailService;
import cn.li.service.OrderService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品订单
 * @author Mr.Wang
 */
@Controller
@RequestMapping("/main")
public class OrderProductDetailController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductDetailService orderProductDetailService;

    /**
     * 打开商品订单页面
     * @param orderId 订单id
     * @param model
     * @return
     */
    @GetMapping("/openProductOrderHtml")
    public String openProductOrder(@RequestParam Integer orderId,@RequestParam Integer orderStatus, Model model){
        List<OrderProductDetail> productOrder = orderProductDetailService.selectOrderProductByOrderId(orderId);
        model.addAttribute("orderType","product");
        model.addAttribute("orderStatus",orderStatus);
        model.addAttribute("productDetails",productOrder);
        return "house/houseOrderList";
    }


    /**
     * 获取所有商品订单
     * @param page
     * @param limit
     * @param orderCode
     * @param clientName
     * @return
     */
    @GetMapping("/getProductOrderList")
    public @ResponseBody
    Map<String, Object> getProductList(@RequestParam Integer page, @RequestParam Integer limit,
                                       @RequestParam(required = false) String orderCode)
    {
        Map<String, Object> mapResult = new HashMap<>();
        try {
            PageInfo pageInfo  = orderService.getProductOrders(page,limit,orderCode);
            mapResult.put("count",orderService.selectAllCount(1));
            mapResult.put("data",pageInfo.getList());
            mapResult.put("msg","成功");
            mapResult.put("code","0");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapResult;
    }


    /**
     * 送餐方法
     * @param productOrderId
     * @return
     */
    @GetMapping("/foodDelivery")
    public @ResponseBody
    Map<String, Object> foodDelivery(Integer productOrderId, HttpSession session){
        Employee employee = (Employee)session.getAttribute("employee");
        Map<String, Object> mapResult = orderProductDetailService.updateProductOrderStatus(productOrderId,employee.getId());
        return mapResult;
    }
}
