package cn.li.controller.admin;

import cn.li.service.SellTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: 销售类型控制器
 */
@Controller
@RequestMapping("/main")
public class SellTypeController {

   @Autowired
   private SellTypeService sellTypeService;

   @RequestMapping("/openSellType")
   public String openSellType(){
       return "sellType/sellTypeList";
   }

}
