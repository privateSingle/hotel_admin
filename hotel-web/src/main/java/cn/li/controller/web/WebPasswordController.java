package cn.li.controller.web;

import cn.li.pojo.Client;
import cn.li.service.ClientService;
import cn.li.service.web.WebPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Mr.Wang
 * @date: 2020/02/11
 * @time: 13:02
 * @comment: 负责忘记密码模块的控制器
 */
@Controller
public class WebPasswordController {

    /**
     * 创建业务层
     */
    @Autowired
    private WebPasswordService webPasswordService;

    /**
     * 客户业务层
     */
    @Autowired
    private ClientService clientService;


    /**
     * 跳转至重置密码页面
     * @return
     */
    @RequestMapping("/resetPassword")
    public String skipResetPassword(HttpSession session){
        //拦截，判断session没有存放id时不可进入该页面
        Object clientId = session.getAttribute("clientId");
        if(clientId == null){
            return "web_page/findPassword";
        }
        return "web_page/resetPassword";
    }

    /**
     * 跳转到修改成功页面
     * @return
     */
    @RequestMapping("/successPwd")
    public String skipEditSuccess(){
        return "web_page/successPwd";
    }

    /**
     * 邮箱修改密码时向邮箱发送验证码
     * @return
     */
    @RequestMapping(value = "/emailSend",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> sendEmailVerifyCode(HttpSession session,String email){
        Map<String, Object> map = new HashMap<>(16);
        //生成验证码，随机四位数字，1000--9999
        int random = (int) (Math.random()*(9999-1000)+1000);
        String cotent = "&nbsp;&nbsp;&nbsp;&nbsp;[Mr Hotels] " +
                "                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;您正在修改Mr Hotels账户密码<br/>" +
                "                    &nbsp;&nbsp;&nbsp;&nbsp;" +
                "                    <span style='font-size:16px;font-weight:bold;color:#4A708B;'>验证码为："+random+"</span><br/>" +
                "                    &nbsp;&nbsp;&nbsp;&nbsp;如非本人操作请勿泄露验证码";
        boolean result = webPasswordService.emailSendVerifyCode(email,"您正在重置Mr Hotels账户密码",cotent);
        //将生成的验证码保存在session中用于验证
        session.setAttribute("random",random);
        //设置session过期时间
        session.setMaxInactiveInterval(15*60);
        map.put("result",result?"success":"error");
        return map;
    }

    /**
     * 注册时向邮箱发送验证码
     * @return
     */
    @RequestMapping(value = "/registryEmailSend",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> sendEmailVerifyCodeByRegistry(HttpSession session,String email){
        Map<String, Object> map = new HashMap<>(16);
        //生成验证码，随机四位数字，1000--9999
        int random = (int) (Math.random()*(9999-1000)+1000);
        String cotent = "&nbsp;&nbsp;&nbsp;&nbsp;[Mr Hotels] " +
                "                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;您正在注册Mr Hotels账户<br/>" +
                "                    &nbsp;&nbsp;&nbsp;&nbsp;" +
                "                    <span style='font-size:16px;font-weight:bold;color:#4A708B;'>验证码为："+random+"</span><br/>" +
                "                    &nbsp;&nbsp;&nbsp;&nbsp;如非本人操作请勿泄露验证码";
        boolean result = webPasswordService.emailSendVerifyCode(email,"您正在注册Mr Hotels账户",cotent);
        //将生成的验证码保存在session中用于验证
        session.setAttribute("random",random);
        //设置session过期时间
        session.setMaxInactiveInterval(15*60);
        map.put("result",result?"success":"error");
        return map;
    }

    /**
     * 校验验证码
     * @return
     */
    @RequestMapping("/verifyRandom")
    @ResponseBody
    public Map<String, Object> verifyRandom(HttpSession session, Integer random, String email){
        Map<String, Object> map = new HashMap<>(16);
        //取出验证码
        Integer randomOld = (Integer) session.getAttribute("random");

        //比较验证码
        if(randomOld != null && randomOld.equals(random)){
            //如果邮箱不为空，则去验证该邮箱是否已注册
            if(email != null){
                Integer count = clientService.selectClientInfoByEmail(email);
                if(count <= 0){
                    map.put("result","success");
                }else{
                    map.put("result","emailError");
                    map.put("msg","该邮箱已被注册！");
                }
            }else{
                map.put("result","success");
            }
        }else if(!randomOld.equals(random)){
            map.put("result","error");
            map.put("msg","验证码输入有误！");
        }else if(randomOld == null){
            map.put("result","error");
            map.put("msg","验证码已过期，请重新获取！");
        }
        return map;
    }

    /**
     * 验证邮箱和用户名是否存在
     * @return
     */
    @RequestMapping(value = "/verifyEmailAndName",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> verifyEmailAndName(Client client, HttpSession session){
        Map<String, Object> map = new HashMap<>(16);
        try {
            Client resultClient = clientService.verifyEmailAndName(client);
            if(resultClient != null){
                //将该用户的ID存入session，修改密码时使用
                session.setAttribute("clientId", resultClient.getId());
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

    /**
     * 根据用户ID修改密码
     * @return
     */
    @RequestMapping("/updateClientPassword")
    @ResponseBody
    public Map<String, Object> updateClientPassword(String password, HttpSession session){
        Map<String, Object> map = new HashMap<>(16);
        try {
            //更新密码
            Client client = new Client();
            client.setId((Integer) session.getAttribute("clientId"));
            client.setPassword(password);
            //调佣更新方法
            Integer updateResult = clientService.updateByPrimaryKeySelective(client);
            if (updateResult > 0) {
                //清空存放的session数据
                session.removeAttribute("clientId");
                map.put("result", "success");
            } else {
                map.put("result", "error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 注册用户时向用户表添加数据
     * @return
     */
    @RequestMapping("/addClientInfo")
    @ResponseBody
    public Map<String, Object> addClientInfo(Client client){
        Map<String, Object> map = new HashMap<>(16);
        //设置积分
        client.setScore(null);
        //设置余额
        client.setBalance(0);
        //设置是否会员
        client.setIsVip(0);
        //设置时间
        client.setCreatTime(System.currentTimeMillis());
        try {
            //调用插入方法
            Integer result = clientService.insertSelective(client);
            if(result > 0){
                map.put("result","success");
            }else{
                map.put("result","error");
                map.put("msg","注册失败！请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","error");
            map.put("msg","注册用户时出现异常！");
        }
        return map;
    }

}
