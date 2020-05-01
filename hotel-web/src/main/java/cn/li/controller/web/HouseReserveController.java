package cn.li.controller.web;

import cn.li.pojo.Client;
import cn.li.pojo.Order;
import cn.li.pojo.OrderHouseDetail;
import cn.li.service.ClientService;
import cn.li.service.OrderHouseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class HouseReserveController {
    @Autowired
    private OrderHouseDetailService orderHouseDetailService;

    @Autowired
    ClientService clientService;

    /**
     * 1,用户id /
     * 2,房间类型id /
     * 3，入住时间 /
     * 4，退房时间 /
     * 5，订单详情id /
     *6，房间价格 /
     * 预定房间
     * @return
     */
    @RequestMapping("/reserver")
    @ResponseBody
    public Map<String,Object> houseReserve(@RequestParam Integer clientId, @RequestParam Integer houseTypeId, @RequestParam String startDate,
                                           @RequestParam String endDate, @RequestParam Integer monery, @RequestParam Integer reserverCount, HttpSession session){
        Client client = (Client) session.getAttribute("clientUser");
        client.setBalance(client.getBalance()-monery);
        session.setAttribute("clientUser",client);

        Date day = new Date();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dft1 = new SimpleDateFormat("yyyyMMddHHmmss");
        Order order = new Order();
        order.setClientId(clientId);
        order.setCreateDate(dft.format(day));
        order.setReserverCount(reserverCount);
        order.setMoney(monery);
        order.setEmpId(null);
        order.setOrderCode(dft1.format(day)+clientId);
        order.setOrderType(0);
        order.setOrderStatus(0);
        order.setIsComment(0);

        OrderHouseDetail orderHouseDetail = new OrderHouseDetail();
        orderHouseDetail.setHouseTypeId(houseTypeId);//客房类型
        orderHouseDetail.setEndDate(endDate);//结束时间
        orderHouseDetail.setMoney(monery/reserverCount);//订单金额
        orderHouseDetail.setStartDate(startDate);//开始时间

        return orderHouseDetailService.houseReserve(order,orderHouseDetail);
    }
}
