package cn.li.controller.admin;

import cn.li.pojo.Client;
import cn.li.pojo.SearchPojo;
import cn.li.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yajun
 * @create 2020/1/7
 * 客户表控制层
 */
@Controller
@RequestMapping("/main")
public class ClientController {

    /**
     * 客户业务层
     */
    @Autowired
    private ClientService clientService;

    /**
     * 页面跳转
     * @return
     */
    @GetMapping("/openClient")
    public String  openClient(){
        return "client/clientList";
    }

    /**
     * 打开修改窗口
     * @param selectId
     * @param model
     * @return
     */
    @RequestMapping("operation/updateClientWindow")
    public String openUpdateClient(Integer selectId, Model model){
        //根据ID查询出要修改的用户ID
        Client client = clientService.selectByPrimaryKey(selectId);
        model.addAttribute("clientInfo", client);
        model.addAttribute("operationType", "client");
        return "operation/updateClientView";
    }

    /**
     * 初始化数据查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/client/initData")
    public @ResponseBody Map<String, Object> initData(Integer page, Integer limit, SearchPojo searchPojo, String clientName){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            //查询数据
            List<Client> clientList = clientService.selectAllInfo(page, limit, searchPojo);
            //查询总条数
            Integer count = clientService.selectCountAll(searchPojo);
            //封装数据
            resultMap.put("data",clientList);
            resultMap.put("msg","成功！");
            resultMap.put("code","0");
            resultMap.put("count",count);
        }catch (Exception e){
            //封装数据
            resultMap.put("data","");
            resultMap.put("msg","查询失败！");
            resultMap.put("code","1");
            resultMap.put("count","-1");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 删除用户信息
     * @return
     */
    @RequestMapping("/delClientInfo")
    public @ResponseBody Map<String, Object> delClientInfo(String ids){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Integer result = clientService.deleteById(ids);
            if(result > 0){     //删除成功
                resultMap.put("result", "success");
                resultMap.put("msg", "删除客户信息成功！");
            }else{              //删除失败
                resultMap.put("result", "error");
                resultMap.put("msg", "删除客户信息失败！");
            }
        }catch (Exception e){
            resultMap.put("result", "error");
            resultMap.put("msg", "删除客户信息出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping("/operation/updateClientInfo")
    public @ResponseBody Map<String, Object> updateClientInfo(Client client){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //System.out.println(client);
            //调用修改方法
            Integer result = clientService.updateByPrimaryKeySelective(client);
            if(result > 0) {
                resultMap.put("result", "success");
                resultMap.put("msg", "修改客户信息成功！");
            }else{
                resultMap.put("result", "error");
                resultMap.put("msg", "修改客户信息失败！");
            }
        }catch (Exception e){
            resultMap.put("result", "error");
            resultMap.put("msg", "修改客户信息出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }
}
