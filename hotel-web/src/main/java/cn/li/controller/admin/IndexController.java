package cn.li.controller.admin;

import cn.li.pojo.Employee;
import cn.li.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mr.Wang
 * @date 2019/1/7
 * 操作类控制器
 */
@Controller
//@RequestMapping("")
public class IndexController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录页
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 登录页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "login";
    }
    
    /**
     * 登录验证
     * @param loginName
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/skipPage")
    public String skipPage(String loginName, String password, Model model, HttpSession session){
        //调用登录方法
        Employee employee = employeeService.selectEmployeeByNameAndPwd(loginName, password);
        if(employee != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String formatDate = simpleDateFormat.format(date);
            session.setAttribute("employee",employee);
            session.setAttribute("loginNowDate",formatDate);
            session.setMaxInactiveInterval(30*60);
            return "redirect:main/mainPage";
        }else{
            model.addAttribute("msg","用户名或密码有误！");
            model.addAttribute("loginName",loginName);
            model.addAttribute("password",password);
            return "login";
        }
    }

    /**
     * 主页面跳转
     * @return
     */
    @RequestMapping("/main/mainPage")
    public String main(){
        return "index";
    }

    /**
     * 欢迎页
     * @return
     */
    @RequestMapping("/main/welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 退出功能
     * @return
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();
        return "login";
    }
}
