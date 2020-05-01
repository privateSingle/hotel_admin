package cn.li.controller.admin;

import cn.li.pojo.Business;
import cn.li.pojo.Employee;
import cn.li.pojo.Product;
import cn.li.pojo.Purchase;
import cn.li.service.BusinessService;
import cn.li.service.ProductService;
import cn.li.service.PurchaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: 采购表控制器
 */
@Controller
@RequestMapping("/main")
public class PurchaseController {

   /**
    * 采购service
    */
   @Autowired
   private PurchaseService purchaseService;

   /**
    * 商品service
    */
   @Autowired
   private ProductService productService;

   /**
    * 营业service
    */
   @Autowired
   private BusinessService businessService;

   @RequestMapping("/openPurchase")
   public String openPurchase(){
       return "purchase/purchaseList";
   }

   /**
    * 初始化页面
    * @param limit
    * @param page
    * @return
    */
   @RequestMapping("purchase/initData")
   public @ResponseBody
   Map<String, Object> initData(Integer limit, Integer page, String name,String time){
      PageInfo<Purchase> selectResult = purchaseService.findAllPurchase(page,limit,name,time);
      List<Purchase> listData = selectResult.getList();
      Map<String, Object> result = new HashMap<>();
      Integer count = purchaseService.findAllPurchaseCount(name, time);
      result.put("msg","");
      result.put("count", count);
      result.put("code","0");
      result.put("data",listData);
      return result;
   }

   /**
    * 打开添加采购信息窗口
    * @return
    */
   @RequestMapping("operation/addPurchaseInfoWindow")
   public String skipAddProductInfoView(Model model,Product product){
      List<Product> list = productService.findAllProduct(product);
      model.addAttribute("lists",list);
      model.addAttribute("operationType","purchase");
      return "operation/addView";
   }

   /**
    * 采购商品
    * @param purchase
    * @return
    */
   @RequestMapping("operation/addPurchaseInfo")
   public @ResponseBody
   Map<String,Object> addPurchaseInfo(Purchase purchase, HttpSession session){
      Map<String,Object> map = new HashMap<>();
      int count = purchaseService.insert(purchase);
      if (count > 0){
         map.put("result","success");
         map.put("msg","采购商品成功!");
         //采购商品成功后,将采购的数量加到商品列表中(也就是在库存中加入采购的数量)
         productService.editProductCount(purchase.getProductId(),purchase.getCount());
         Employee employee = (Employee) session.getAttribute("employee");
         Business business = new Business();
         business.setSellDate(purchase.getPurchaseDate());
         business.setPurchaseId(purchase.getId());
         business.setPrice(purchase.getTotalPrice());
         business.setSellTypeId(3);
         business.setEmpId(employee.getId());
         businessService.insertSelective(business);
      }else{
         map.put("result","error");
         map.put("msg","采购商品失败!");
      }
      return map;
   }

   /**
    * 打开修改采购信息窗口
    * @return
    */
   @RequestMapping("operation/updatePurchaseInfoWindow/{result}")
   public String skipUpdateProductInfoView(@PathVariable("result") Integer result,Model model, Product product){
      Purchase purchase = purchaseService.selectByPrimaryKey(result);//查询要修改的采购信息
      List<Product> list = productService.findAllProduct(product);//查询所有商品信息
      model.addAttribute("purchases",purchase);
      model.addAttribute("lists",list);
      model.addAttribute("operationType","purchase");
      return "operation/updateProductView";
   }

   /**
    * 修改采购信息
    * @param purchase
    * @return
    */
   @RequestMapping("operation/updatePurchaseInfoWindow/updatePurchaseInfo")
   public @ResponseBody
   Map<String,Object> updatePurchaseInfo(Purchase purchase){
      Map<String,Object> map = new HashMap<>();
      int count = purchaseService.updateByPrimaryKey(purchase);
      if (count > 0){
         map.put("result","success");
         map.put("msg","修改采购信息成功!");
         //修改采购信息成功后,将采购的数量加到商品列表中(也就是在库存中加入采购的数量)
         productService.editProductCount(purchase.getProductId(),purchase.getCount()-purchase.getBeforeCount());
         //修改采购数量后,营业表中的金额也随之修改
         businessService.updateBusinessByPurchaseId(purchase.getId(),purchase.getTotalPrice());
      }else{
         map.put("result","error");
         map.put("msg","修改采购信息失败!");
      }
      return map;
   }

   /**
    * 删除采购信息
    * @param delIds
    * @return
    */
   @RequestMapping("/delPurchaseInfo")
   public @ResponseBody
   Map<String,Object> delProductType(String delIds){
      Map<String,Object> map = new HashMap<>();
      int count = purchaseService.delPurchaseInfo(delIds);
      if(count > 0){
         map.put("result","success");
         map.put("msg","删除采购记录成功!");
         //调用删除营业表记录方法
         Integer resultDelBusiness = businessService.deleteByPurchaseId(delIds);
      }else{
         map.put("result","error");
         map.put("msg","删除采购记录失败!");
      }
      return map;
   }
}
