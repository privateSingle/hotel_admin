package cn.li.controller.admin;

import cn.li.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: 权限表控制器
 */
@Controller
@RequestMapping("/main")
public class PowerController {

   @Autowired
   private PowerService powerService;

   @RequestMapping("/openPower")
   public String openPower(){
      return "power/powerList";
   }

}
