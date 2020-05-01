package cn.li.controller.web;

import cn.li.pojo.*;
import cn.li.service.ClientService;
import cn.li.service.CommentService;
import cn.li.service.HouseTypeService;
import cn.li.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Mr.Wang
 * @date: 2020/02/10
 * @time: 15:09
 * @comment: 用户个人信息的控制器
 */
@Controller
@RequestMapping("/member")
public class WebMemberController {

    /**
     * 用户业务层
     */
    @Autowired
    private ClientService clientService;

    /**
     * 评价表业务层
     */
    @Autowired
    private CommentService commentService;

    /**
     * 订单表业务层
     */
    @Autowired
    private OrderService orderService;

    /**
     * 房间类型业务层
     */
    @Autowired
    private HouseTypeService houseTypeService;

    /**
     * 跳转至用户页面
     * @return
     */
    @RequestMapping("/memberIndexPage")
    public String member() {
        return "web_page/member/member";
    }

    /**
     * 跳转至用户安全页面
     *
     * @return
     */
    @RequestMapping("/memberSafe")
    public String memberSafe() {
        return "web_page/member/memberSafe";
    }

    /**
     * 跳转至用户订单页面
     * @return
     */
    @RequestMapping("/memberOrder")
    public String memberOrder() {
        return "web_page/member/memberOrder";
    }

    /**
     * 跳转至充值页面
     * @return
     */
    @RequestMapping("/skipRechargeMoney")
    public String skipRechargeMoney(){
        return "web_page/member/rechargeMoney";
    }

    /**
     * 跳转至写评价页面
     * @param orderId 获取订单号，根据订单号查出订单详情中该客户入住的房间类型
     * @return
     */
    @RequestMapping("/skipWriteComment")
    public String skipWriteComment(Integer orderId, Model model){
        //查询房间类型ID
        Integer typeId = orderService.selectHouseTypeByOrderId(orderId);
        List<HouseType> typeList = houseTypeService.selectAllHouseType();
        //数据存入model
        model.addAttribute("typeId", typeId);
        model.addAttribute("typeList", typeList);
        model.addAttribute("orderId", orderId);
        return "web_page/member/writeComment";
    }

    /**
     * 个人中心的订单信息展示
     *
     * @return
     */
    @RequestMapping("/webOrder/initData")
    @ResponseBody
    public Map<String, Object> initData(Integer page, Integer limit, HttpSession session) {
        //查询该用户的所有评论
        Map<String, Object> map = new HashMap<>(16);
        try {
            //查询订单
            List<MemberOrder> orderList = orderService.selectAllInfo(page, limit, ((Client) session.getAttribute("clientUser")).getId());
            //查询总条数
            Integer count = orderService.selectCountAllByClientId(((Client) session.getAttribute("clientUser")).getId());
            //封装数据
            map.put("data", orderList);
            map.put("msg", "成功！");
            map.put("code", "0");
            map.put("count", count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 跳转至用户评价页面
     *
     * @return
     */
    @RequestMapping("/memberComments")
    public String memberComments(Model model, HttpSession session, Integer page) {
        if (page == null || page <= 0) {
            page = 1;
        }
        Integer limit = 5;
        //查询该用户的所有评论
        Client client = (Client) session.getAttribute("clientUser");
        //查询方法
        List<Comment> commentList = commentService.selectAllByPage(page, limit, client.getId());
        //查询全部记录数量
        Integer count = commentService.selectAllCountByClientId(client.getId());
        model.addAttribute("comments", commentList);
        model.addAttribute("count", count);
        //当前页
        model.addAttribute("page", page);
        //总页数
        model.addAttribute("totalPage", count % limit == 0 ? count / limit : count / limit + 1);
        return "web_page/member/memberComments";
    }

    /**
     * 账户安全页面的操作
     *
     * @return
     */
    @RequestMapping("/editClientInfo")
    @ResponseBody
    public Map<String, Object> editMemberSafe(Client client, HttpSession session) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            //取出当前登录用户的ID存入要修改的Client对象
            client.setId(((Client) session.getAttribute("clientUser")).getId());
            //调用修改方法
            Integer result = clientService.updateByPrimaryKeySelective(client);
            if (result > 0) {
                map.put("result", "success");
            } else {
                map.put("result", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 账户修改成功后的跳转
     * @return
     */
    @RequestMapping("/successSkip")
    public String successSkip(HttpSession session) {
        //清除本次信息
        session.invalidate();
        return "redirect:/webLogin";
    }

    /**
     * 删除评价信息
     * @return
     */
    @RequestMapping("/deleteCommentInfo")
    @ResponseBody
    public Map<String, Object> deleteCommentInfo(Integer commentId) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            //调用删除方法
            Integer result = commentService.deleteByPrimaryKey(commentId);
            if (result > 0) {
                map.put("result", "success");
                map.put("msg", "删除成功！");
            } else {
                map.put("result", "error");
                map.put("msg", "删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 用户取消订单
     * @return
     */
    @RequestMapping("/updateOrderStatus")
    @ResponseBody
    public Map<String, Object> updateOrderStatus(HttpSession session, String orderCode) {
        Map<String, Object> map = new HashMap<>(16);
        if (!isSessionExpiration(session)) {
            map.put("result", "error");
            map.put("msg", "登录会话过期！请刷新本页面！");
        }
        try {
            //调用更新方法
            Integer result = orderService.updateOrderStatusByClientIdAndOrderId(((Client) session.getAttribute("clientUser")).getId(), orderCode);
            if (result > 0) {
                map.put("result", "success");
                map.put("msg", "订单成功取消！");
            } else {
                map.put("result", "error");
                map.put("msg", "订单取消失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据ID删除一条订单信息
     * @param orderId
     * @return
     */
    @RequestMapping("/deleteOrderInfoByOrderId")
    @ResponseBody
    public Map<String, Object> deleteOrderInfoByOrderId(Integer orderId, HttpSession session) {
        Map<String, Object> map = new HashMap<>(16);
        if (!isSessionExpiration(session)) {
            map.put("result", "error");
            map.put("msg", "登录会话过期！请刷新本页面！");
        }
        try {
            //调用删除方法
            Integer result = orderService.deleteByPrimaryKey(orderId);
            if (result > 0) {
                map.put("result", "success");
                map.put("msg", "订单删除成功！");
            } else {
                map.put("result", "error");
                map.put("msg", "订单删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 会话是否已过期
     * @param session
     * @return
     */
    public boolean isSessionExpiration(HttpSession session) {
        Client client = (Client) session.getAttribute("clientUser");
        if (client == null) {
            return false;
        }
        return true;
    }

    /**
     * 修改用户为会员
     * @return
     */
    @RequestMapping("/updateClientIsVip")
    @ResponseBody
    public Map<String, Object> updateClientIsVip(HttpSession session){
        //要返回的Map
        Map<String, Object> map = new HashMap<>(16);
        try {
            //取出当前登录用户
            Client client = (Client)session.getAttribute("clientUser");
            //判断费用是否足够
            if(client.getBalance() == null || client.getBalance() < 10000){
                map.put("result", "error");
                map.put("msg", "账户余额不足以支付！请先充值余额！");
                return map;
            }
            //扣除费用
            client.setBalance(client.getBalance()-10000);
            //修改会员标识
            client.setIsVip(1);
            //修改积分
            client.setScore(0);
            //调用业务层修改方法
            Integer result = clientService.updateByPrimaryKeySelective(client);
            if(result > 0){
                map.put("result", "success");
            }else{
                map.put("result", "error");
                map.put("msg", "申请会员失败！请稍后再试！");
            }
        }catch (Exception e){
            map.put("result", "error");
            map.put("msg", "申请会员出现异常！");
        }
        return map;
    }

    /**
     * 用户充值方法
     * @param score     赠送的积分数量
     * @param money     充值金额
     * @param session   会话对象
     * @return
     */
    @RequestMapping("/addClientScore")
    @ResponseBody
    public Map<String, Object> addClientScore(Integer score, Integer money, HttpSession session){
        Map<String, Object> map = new HashMap<>(16);
        try{
            //取出当前登录对象
            Client client = (Client) session.getAttribute("clientUser");
            //设置余额
            client.setBalance(client.getBalance()+money*100);
            //判断积分是否为空
            if(score != null) {
                //设置积分
                client.setScore(client.getScore() + score);
            }
            //调用更新方法
            Integer result = clientService.updateByPrimaryKeySelective(client);
            if(result > 0){
                map.put("result","success");
            }else{
                map.put("result","error");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 添加一条评价信息
     * @param comment   评价对象
     * @return
     */
    @RequestMapping("/insertCommentInfo")
    @ResponseBody
    public Map<String, Object> insertCommentInfo( Integer orderId, Comment comment, HttpSession session){
        Map<String, Object> map = new HashMap<>(16);
        try {
            //设置当前登录人的ID
            comment.setClientId(((Client)session.getAttribute("clientUser")).getId());
            //获取当前时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comment.setDate(format.format(new Date()));
            //对内容进行解码处理，因为textarea文本默认是GBK
            //comment.setContent(new String(comment.getContent().getBytes("iso-8859-1"),"utf-8"));
            //更改订单中的isComment表示为已评价
            Order order = new Order();
            order.setIsComment(1);
            order.setId(orderId);
            //调用插入方法插入评价，同时修改订单的评价状态为已评价
            Integer result = commentService.insertSelective(comment, order);
            //评价插入成功
            if(result > 0){
                map.put("result","success");
            }else{
                map.put("result","error");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","error");
        }
        return map;
    }


}
