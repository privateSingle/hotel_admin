package cn.li.controller.admin;

import cn.li.pojo.Business;
import cn.li.pojo.SearchPojo;
import cn.li.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yajun
 * @create 2020/1/7
 * 营业日报表控制层
 */
@Controller
@RequestMapping("/main")
public class BusinessController {

    /**
     * 营业表业务层
     */
    @Autowired
    private BusinessService businessService;

    /**
     * 营业报表页面跳转
     * @return
     */
    @GetMapping("/openBusiness")
    public String openBusiness(){
        return "business/businessList";
    }

    /**
     * 商品销售报表页面跳转
     * @return
     */
    @GetMapping("/openBusinessProduct")
    public String openBusinessProduct(){
        return "business/businessProductLise";
    }

    /**
     * 住房报表页面跳转
     * @return
     */
    @GetMapping("/openBusinessHouse")
    public String openBusinessHouse(){
        return "business/businessHouseLise";
    }

    /**
     * 采购报表页面跳转
     * @return
     */
    @GetMapping("/openBusinessPurchase")
    public String openBusinessPurchase(){
        return "business/businessPurchaseList";
    }

    /**
     * 图表页面跳转
     * @return
     */
    @RequestMapping("/openBusinessImgTable")
    public String openBusinessImgTable(){
        return "business/businessTableList";
    }

    /**
     * 初始化查询数据
     * @return
     */
    @RequestMapping("business/initData")
    public @ResponseBody Map<String, Object> initData(Integer page, Integer limit, SearchPojo searchPojo){
        Map<String, Object> mapResult = new HashMap<>();
        try {
            List<Business> businessList = businessService.selectAllInfo(page, limit, searchPojo);
            //记录收入
            Integer income = 0;
            //记录支出
            Integer expend = 0;
            //循环计算支出和收入
            for (Business business:businessList) {
                //判断sellType的ID，1：收银（收入） 2：销售（收入） 3：采购（支出）
                if(business.getSellTypeId() == 3){
                    expend += business.getPrice()/100;
                }else{
                    income += business.getPrice()/100;
                }
            }
            mapResult.put("count",businessService.selectAllCount(searchPojo));
            mapResult.put("data",businessList);
            mapResult.put("msg","查询成功！");
            mapResult.put("code",0);
            mapResult.put("income",income);
            mapResult.put("expend",expend);
        }catch (Exception e){
            mapResult.put("count","");
            mapResult.put("data","");
            mapResult.put("msg","查询失败");
            mapResult.put("code",1);
            e.printStackTrace();
        }
        return mapResult;
    }
}
