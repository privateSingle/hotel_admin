package cn.li.controller.admin;

import cn.li.pojo.Client;
import cn.li.pojo.Employee;
import cn.li.pojo.SearchPojo;
import cn.li.service.ClientService;
import cn.li.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yajun
 * @create 2020/1/7
 * @comment 员工表控制器
 */
@Controller
@RequestMapping("/main")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 打开员工信息页面
     * @return
     */
    @GetMapping("/openEmployee")
    public String openEmployee(){
        return "employee/employeeList";
    }

    /**
     * 打开新增页面
     * @return
     */
    @GetMapping("/operation/addEmployeeInfoWindow")
    public String openAddEmployeeInfo(Model model){
        model.addAttribute("operationType","employee");
        return "operation/addClientView";
    }

    /**
     * 打开修改信息窗口
     * @return
     */
    @RequestMapping("/operation/openUpdateEmployeeTypeHtml")
    public String openUpdateEmployeeTypeHtml(Integer id, Model model){
        //根据ID查询出要修改的用户ID
        Employee employee = employeeService.selectByPrimaryKey(id);
        model.addAttribute("employeeInfo", employee);
        model.addAttribute("operationType", "employee");
        return "operation/updateClientView";
    }

    /**
     * 初始化数据查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/employee/initData")
    public @ResponseBody
    Map<String, Object> initData(Integer page, Integer limit){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            //查询数据
            List<Employee> employeeList = employeeService.selectEmployeeAllInfo(page, limit);
            //查询总条数
            Integer count = employeeService.selectEmployeeCountAll();
            //封装数据
            resultMap.put("data",employeeList);
            resultMap.put("msg","成功！");
            resultMap.put("code","0");
            resultMap.put("count",count);
        }catch (Exception e){
            resultMap.put("data","");
            resultMap.put("msg","查询失败！");
            resultMap.put("code","0");
            resultMap.put("count","");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 修改员工信息
     * @return
     */
    @RequestMapping(value={"/employee/editEmployeeInfo","/operation/editEmployeeInfo"})
    public @ResponseBody Map<String, Object> editEmployeeInfo(Employee employee){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //调用修改方法
            Integer result = employeeService.updateByPrimaryKey(employee);
            //判断是否成功
            if(result > 0){
                resultMap.put("result","success");
                resultMap.put("msg","修改员工信息成功！");
            }else{
                resultMap.put("result","error");
                resultMap.put("msg","修改员工信息失败！");
            }
        }catch (Exception e){
            resultMap.put("result","error");
            resultMap.put("msg","修改员工信息出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 修改员工信息
     * @return
     */
    @RequestMapping("/operation/addEmployeeInfo")
    public @ResponseBody Map<String, Object> addEmployeeInfo(Employee employee){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //调用修改方法
            Integer result = employeeService.insertSelective(employee);
            //判断是否成功
            if(result > 0){
                resultMap.put("result","success");
                resultMap.put("msg","新增员工信息成功！");
            }else{
                resultMap.put("result","error");
                resultMap.put("msg","新增员工信息失败！");
            }
        }catch (Exception e){
            resultMap.put("result","error");
            resultMap.put("msg","新增员工信息出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 删除员工信息
     * @return
     */
    @RequestMapping("/employee/deleteEmployeeInfo")
    public @ResponseBody Map<String, Object> deleteEmployeeInfo(String ids){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //调用修改方法
            Integer result = employeeService.deleteByPrimaryKey(ids);
            //判断是否成功
            if(result > 0){
                resultMap.put("result","success");
                resultMap.put("msg","删除员工信息成功！");
            }else{
                resultMap.put("result","error");
                resultMap.put("msg","删除员工信息失败！");
            }
        }catch (Exception e){
            resultMap.put("result","error");
            resultMap.put("msg","删除员工信息出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }
}
