package cn.li.controller.web;

import cn.li.pojo.*;
import cn.li.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebProductController {

    @Autowired
    private DishesService dishesService;

    @Autowired
    private BuyCarService buyCarService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductDetailService orderProductDetailService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;
    /**
     * 跳转餐品页面
     * @return
     */
    @RequestMapping("/productPage")
    public String productPage(Model model,@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                              @RequestParam(defaultValue="15",value="pageSize")Integer pageSize,String name,HttpSession session){
        Client client = (Client) session.getAttribute("clientUser");
        if (client == null){
            return "redirect:webLogin";
        }
        List<String> houseCode = buyCarService.findHouseCodeBuyClientId(client.getId());
        session.setAttribute("houseCode",houseCode);
        if(pageNum == null){
            //设置默认当前页
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            //设置默认每页显示的数据数
            pageSize = 15;
        }
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<DiAndPr> list = dishesService.findAll(name);
        PageInfo<DiAndPr> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("barType","product");
        return "web_page/product";
    }

    @RequestMapping("/productPageSkip")
    public @ResponseBody Map<String,Object> productPageSkip(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        Client client = (Client) session.getAttribute("clientUser");
        List<String> houseCode = buyCarService.findHouseCodeBuyClientId(client.getId());
        if (houseCode == null || houseCode.size() == 0){
            map.put("result","error");
        }else{
            map.put("result","success");
        }
        return map;
    }


    /**
     * 向购物车添加一条
     * @param buyCar
     * @param session
     * @return
     */
    @RequestMapping("/addBuyCar")
    public @ResponseBody Map<String,Object> addBuyCar(BuyCar buyCar,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        Client client = (Client) session.getAttribute("clientUser");
        BuyCar buyCar1 = buyCarService.findBuyCar(buyCar.getProId(),buyCar.getProType(),client.getId());
        if (buyCar1 == null){
            buyCar.setClientId(client.getId());
            int count = buyCarService.addBuyCar(buyCar);
        }else{
            buyCar1.setClientId(client.getId());
            buyCar1.setCount(buyCar1.getCount()+1);
            int count = buyCarService.updateBuyCar(buyCar1);
        }
        map.put("result","success");
        return map;
    }

    /**
     * 跳出购物车页面
     * @param buyCar
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/skipBuyCar")
    public String addBuyCar(BuyCar buyCar,HttpSession session,Model model){
        Client client = (Client) session.getAttribute("clientUser");
        List<BuyCar> list = buyCarService.findAllBuyCar(client.getId());
        Integer sum = 0;
        for (BuyCar car : list) {
            sum = sum+(car.getPrice()*car.getCount());
        }
        List<String> houseCode = (List<String>) session.getAttribute("houseCode");
        model.addAttribute("client",client);
        model.addAttribute("houseCode",houseCode);
        model.addAttribute("sum",sum);
        model.addAttribute("buyCarInfo",list);
        return "web_page/buyCar";
    }

    /**
     * 删除购物车记录
     * @param id
     * @return
     */
    @RequestMapping("/deleteBuyCar")
    public @ResponseBody Map<String,Object> deleteBuyCarById(Integer id){
        Map<String,Object> map = new HashMap<>();
        if(id != null){
            int count = buyCarService.deleteBuyCarById(id);
            if (count>0){
                map.put("result","success");
            }
        }
        return map;
    }

    /**
     * 下单(向订单表order插入一条数据)
     * @param sum
     * @param session
     * @return
     */
    @RequestMapping("/orderBuyCar")
    public @ResponseBody Map<String,Object> orderBuyCar(Integer sum,HttpSession session){
        Map<String,Object> map = new HashMap<>(16);
        //客户ID
        Client client = (Client) session.getAttribute("clientUser");
        Order order = new Order();
        order.setClientId(client.getId());
        order.setEmpId(null);
        order.setOrderType(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setCreateDate(format.format(new Date()));
        order.setMoney(sum*100);
        format = new SimpleDateFormat("yyyyMMddHHmmss");
        order.setOrderCode(format.format(new Date())+client.getId());
        order.setReserverCount(null);
        order.setIsComment(2);
        order.setOrderStatus(0);
        int count = orderService.insert(order);
        int count2 = clientService.orderSettlement(client.getId(),sum*100);//修改客户余额
        if (count>0 && count2>0){
            client.setBalance(client.getBalance()-sum*100);
            session.setAttribute("clientUser",client);//将新的客户余额存入session的客户对象中
            map.put("result","success");
            map.put("orderId",order.getId());
        }else{
            map.put("result","error");
        }
        return map;
    }

    /**
     * 下单成功(向餐品订单表插入数据并删除购物车该用户的记录)
     * @param list
     * @param session
     * @return
     */
    @RequestMapping("/orderProduct")
    public @ResponseBody Map<String,Object> orderProduct(@RequestBody List<OrderProductDetail> list,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        Client client = (Client) session.getAttribute("clientUser");//客户ID
        int count = 0;
        for (OrderProductDetail orderProductDetail : list) {
            count = orderProductDetailService.insert(orderProductDetail);
            if (orderProductDetail.getProType() == 1){
                productService.updateProductCountById(orderProductDetail.getProductId(),orderProductDetail.getCount());
            }
        }
        if (count > 0){
            int count2 = buyCarService.deleteBuyCarByClientId(client.getId());
            if (count2 > 0){
                map.put("result","success");
            }
        }
        return map;
    }
}
