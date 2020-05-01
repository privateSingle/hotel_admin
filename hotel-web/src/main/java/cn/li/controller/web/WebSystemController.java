package cn.li.controller.web;

import cn.li.mapper.HouseTypeMapper;
import cn.li.pojo.Client;
import cn.li.pojo.Comment;
import cn.li.pojo.House;
import cn.li.pojo.HouseType;
import cn.li.service.ClientService;
import cn.li.service.CommentService;
import cn.li.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: Mr.Wang
 * @date: 2020/02/05
 * @time: 12:27
 * @comment: 前台页面的跳转控制
 */
@Controller
public class WebSystemController {

    /**
     * 用户业务层
     */
    @Autowired
    private ClientService clientService;

    /**
     * 用户评价业务层
     */
    @Autowired
    private CommentService commentService;

    /**
     * 客房类型业务层
     */
    @Autowired
    private HouseTypeMapper houseTypeMapper;

    /**
     * 项目启动默认打开页面
     * @return
     */
    @RequestMapping("/initPage")
    public String initPage(Model model){
        model.addAttribute("barType","index");
        return "web_page/index";
    }

    /**
     * 跳转到登录页
     * @return
     */
    @RequestMapping("/webLogin")
    public String webLoginPage(Model model){
        model.addAttribute("type","login");
        return "web_page/login";
    }

    /**
     * 跳转到注册页
     * @return
     */
    @RequestMapping("/webRegistry")
    public String webRegistry(Model model){
        model.addAttribute("type","registry");
        return "web_page/login";
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/webLoginVerify")
    public String webLoginVerify(Client client, HttpSession session){
        session.removeAttribute("msg");
        session.removeAttribute("username");
        //调用数据库验证登录
        Client clientResult = clientService.loginVerifyByPassword(client);
        //判断是否登录成功
        if(clientResult == null){
            //登录失败
            session.setAttribute("msg","登录信息不匹配！请新输入！");
            session.setAttribute("username",client.getEmail() == null?client.getEmail():client.getPhone());
            return "redirect:webLogin";
        }else{
            //登陆成功存入session
            session.setAttribute("clientUser", clientResult);
            session.setMaxInactiveInterval(30*60);
            //跳转
            return "redirect:initPage";
        }
    }

    /**
     * 退出功能
     * @return
     */
    @RequestMapping("/webLoginOut")
    public String webLoginOut(HttpSession session, Model model){
        session.invalidate();
        model.addAttribute("type","login");
        return "web_page/login";
    }

    /**
     * 跳转到预订房间页面
     * @return
     */
    @RequestMapping("/room")
    public String skipRoom(Model model){
        List<HouseType> houseTypes = houseTypeMapper.selectAllHouseType();
        model.addAttribute("houseTypes",houseTypes);
        model.addAttribute("barType","room");
        return "web_page/room";
    }

    /**
     * 跳转到房间详情页面
     * @return
     */
    @RequestMapping("/roomPage")
    public String skipRoomPage(){
        return "web_page/room_page";
    }

    /**
     * 跳转到关于我们页面
     * @return
     */
    @RequestMapping("/about")
    public String skipAbout(Model model){
        model.addAttribute("barType","about");
        return "web_page/about";
    }

    /**
     * 跳转到房间详情页面
     * @return
     */
    @RequestMapping("/single")
    public String skipSingle(Model model, Integer id, HttpSession session){
        Object obj = session.getAttribute("clientUser");
        //判断是否登录
        if(obj == null){
            return "redirect:webLogin";
        }
        HouseType houseType = houseTypeMapper.selectByPrimaryKey(id);
        List<Comment> comments = commentService.getComments(id);
        model.addAttribute("houseType",houseType);
        model.addAttribute("comments",comments);
        model.addAttribute("barType","single");
        return "web_page/single";
    }

    /**
     * 跳转到忘记密码页面
     * @return
     */
    @RequestMapping("/findPassword")
    public String findPassword(){
        return "web_page/findPassword";
    }

    /**
     * 跳转到邮箱号修改密码页面
     * @return
     */
    @RequestMapping("/emailFind")
    public String emailFind(){
        return "web_page/emailFind";
    }

    /**
     * 跳转到手机号修改密码页面
     * @return
     */
    @RequestMapping("/phoneFind")
    public String phoneFind(){
        return "web_page/phoneFind";
    }

    /**
     * 跳转至用户页面
     * @return
     */
    @RequestMapping("/member")
    public String member(){
        return "web_page/member";
    }

}
