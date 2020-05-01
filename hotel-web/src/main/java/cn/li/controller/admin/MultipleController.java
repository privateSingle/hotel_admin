package cn.li.controller.admin;

import cn.li.pojo.Multiple;
import cn.li.service.MultipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: 连锁店表控制器
 */
@Controller
@RequestMapping("/main")
public class MultipleController {

   @Autowired
   private MultipleService multipleService;

    /**
     * 页面跳转
     * @return
     */
   @RequestMapping("/openMultiple")
   public String openMultiple(){
       return "multiple/multipleList";
   }

   /**
    * 初始化数据查询
    * @param page
    * @param limit
    * @return
    */
   @RequestMapping("/multiple/initData")
   public @ResponseBody Map<String, Object> initData(Integer page, Integer limit){
      Map<String, Object> mapResult = new HashMap<>();
      try {
         mapResult.put("data",multipleService.selectAllInfo(page, limit));
         mapResult.put("count",multipleService.selectAllCount());
         mapResult.put("code","0");
         mapResult.put("msg","查询成功！");
      }catch (Exception e){
         mapResult.put("data","");
         mapResult.put("count","");
         mapResult.put("code","1");
         mapResult.put("msg","查询出现异常！");
         e.printStackTrace();
      }
      return mapResult;
   }

   /**
    * 修改连锁店信息
    * @return
    */
   @RequestMapping("/multiple/editMultipleInfo")
   public @ResponseBody Map<String, Object> editMultipleInfo(Multiple multiple){
      Map<String, Object> mapResult = new HashMap<>();
      try {
         //调用修改方法
         Integer result = multipleService.updateByPrimaryKey(multiple);
         //判断是否成功
         if(result > 0){
            mapResult.put("result","success");
            mapResult.put("msg","修改分店信息成功！");
         }else{
            mapResult.put("result","error");
            mapResult.put("msg","修改分店信息失败！");
         }
      }catch (Exception e){
         mapResult.put("result","error");
         mapResult.put("msg","修改分店信息出现异常！");
         e.printStackTrace();
      }

      return mapResult;
   }

   /**
    * 打开连锁店新增窗口
    * @return
    */
   @RequestMapping("/operation/addMultipleInfoWindow")
   public String operationAddMultipleInfoWindow(Model model){
       //标识本次是连锁店操作
       model.addAttribute("operationType", "multiple");
       //跳转至新增页面
       return "operation/addClientView";
   }

    /**
     * 添加一条连锁店信息
     * @return
     */
    @RequestMapping("/operation/addMultipleInfo")
   public @ResponseBody Map<String, Object> addMultipleInfo(Multiple multiple){
       Map<String, Object> mapResult = new HashMap<>();
       try {
           //调用插入方法
           Integer result = multipleService.insertSelective(multiple);
           //判断是否成功
           if(result > 0){
               mapResult.put("result","success");
               mapResult.put("msg","新增分店信息成功！");
           }else{
               mapResult.put("result","error");
               mapResult.put("msg","新增分店信息失败！");
           }
       }catch (Exception e){
           mapResult.put("result","error");
           mapResult.put("msg","新增分店信息出现异常！");
           e.printStackTrace();
       }
       return mapResult;
   }

    /**
     * 删除连锁店信息
     * @param ids
     * @return
     */
    @RequestMapping("/multiple/deleteMultipleInfo")
   public @ResponseBody Map<String, Object> deleteMultipleInfo(String ids){
        Map<String, Object> mapResult = new HashMap<>();
        try {
            //调用删除方法
            Integer result = multipleService.deleteMultipleInfo(ids);
            //判断是否成功
            if(result > 0){
                mapResult.put("result","success");
                mapResult.put("msg","删除分店信息成功！");
            }else{
                mapResult.put("result","error");
                mapResult.put("msg","删除分店信息失败！");
            }
        }catch (Exception e){
            mapResult.put("result","error");
            mapResult.put("msg","删除分店信息出现异常！");
            e.printStackTrace();
        }
        return mapResult;
   }

}
