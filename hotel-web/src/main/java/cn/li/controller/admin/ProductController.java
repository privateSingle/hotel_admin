package cn.li.controller.admin;

import cn.li.pojo.Product;
import cn.li.pojo.ProductType;
import cn.li.service.ProductService;
import cn.li.service.ProductTypeService;
import cn.li.utils.FileUploadUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @author : Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: 商品表控制器
 */
@Controller
@RequestMapping("/main")
public class ProductController {

   @Autowired
   private ProductService productService;

   @Autowired
   private ProductTypeService productTypeService;

    /**
     * 打开商品列表页面
     * @param model
     * @return
     */
   @RequestMapping("/openProduct")
   public String openProduct(Model model){
       List<ProductType> list = productTypeService.findAllProductType();//所有商品类型
      model.addAttribute("lists",list);
      return "product/productList";
   }

    /**
     * 初始化页面
     * @param limit
     * @param page
     * @param product
     * @return
     */
   @RequestMapping("product/initData")
   public @ResponseBody
   Map<String, Object> initData(Integer limit, Integer page,Product product){
      PageInfo<Product> selectResult = productService.findAllProduct(page,limit,product);
      List<Product> listData = selectResult.getList();
      Map<String, Object> result = new HashMap<>();
      Integer count = productService.findAllProductCount(product);
      result.put("msg","");
      result.put("count", count);
      result.put("code","0");
      result.put("data",listData);
      return result;
   }
    /**
     * 打开添加商品窗口
     * @return
     */
    @RequestMapping("operation/addProductInfoWindow")
    public String skipAddProductInfoView(Model model,HttpServletRequest req){
        List<ProductType> list = productTypeService.findAllProductType();//所有商品类型
        model.addAttribute("lists",list);
        model.addAttribute("operationType","product");
        return "operation/addView";
    }

    /**
     * 上传图片
     * @return
     */
    public static Map<String,Object> uploadImage(MultipartFile file,HttpServletRequest req) throws IOException{
        //返回的Map
        Map<String, Object> returnMap = new HashMap<>();
        System.out.println("开始上传！");
        //设置上传文件的目标路径
        String path = req.getServletContext().getRealPath("/")+"upload/product/";
        String delName = (String) req.getSession().getAttribute("fileName");//获取图片名
        if (delName != null){
            FileUploadUtil.delFileUpload(path+delName);
        }
        try {
            String fileName = FileUploadUtil.fileUpload(file,path);//获取要上传的文件名
            req.getSession().setAttribute("fileName",fileName);//图片名存入session
            returnMap.put("result","success");
            return returnMap;
        }catch (Exception e){
            e.printStackTrace();
            returnMap.put("result","error");
            return returnMap;
        }
    }

    /**
     * 上传添加商品图片
     * @param file
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping("operation/uploads")
    public @ResponseBody
    Map<String,Object> uploadAddImage(MultipartFile file,HttpServletRequest req) throws IOException{
        Map<String,Object> map = uploadImage(file,req);
        return map;
    }
    /**
     * 上传修改商品图片
     * @param file
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping("operation/updateProductInfoWindow/uploads")
    public @ResponseBody
    Map<String,Object> uploadUpdateImage(MultipartFile file,HttpServletRequest req) throws IOException{
        Map<String,Object> map = uploadImage(file,req);
        return map;
    }

    /**
     * 添加商品信息
     * @param product
     * @param session
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping("/operation/addProduct")
    public @ResponseBody
    Map<String,Object> addProduct(Product product, HttpSession session,HttpServletRequest req) throws IOException{
        Map<String,Object> map = new HashMap<>();
        String fileName = (String)session.getAttribute("fileName");//取出存入的图片名
        if (fileName!=null&&!"".equals(fileName)){
            product.setProductPicture(fileName);
        }
        int addResult = productService.insert(product);  //调用方法
        if(addResult > 0){
            session.removeAttribute("fileName");
            map.put("result","success");
            map.put("msg","添加商品信息成功!");
        }else{
            map.put("result","error");
            map.put("msg","添加商品信息失败!");
        }
        return map;
    }


    /**
     * 打开修改商品窗口
     * @param result
     * @return
     */
    @RequestMapping("operation/updateProductInfoWindow/{result}")
    public String skipUpdateInfoView(@PathVariable("result") Integer result, Model model,HttpSession session){
        Product product = productService.selectByPrimaryKey(result);//查询要修改商品信息
        List<ProductType> productTypes = productTypeService.findAllProductType();//查询出全部的商品类型信息
        session.setAttribute("fileName",product.getProductPicture());
        model.addAttribute("productInfo",product);
        model.addAttribute("types",productTypes);
        model.addAttribute("operationType","product");
        return "operation/updateProductView";
    }

    /**
     * 修改商品信息
     * @param product
     * @param req
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/operation/updateProductInfoWindow/updateProduct")
    public @ResponseBody
    Map<String,Object> updateProduct(Product product, HttpServletRequest req, HttpSession session) throws IOException{
        Map<String,Object> map = new HashMap<>();
        String fileName = (String)session.getAttribute("fileName");//取出存入的图片名
        if (fileName!=null&&!"".equals(fileName)){
            product.setProductPicture(fileName);
        }
        int addResult = productService.updateByPrimaryKey(product);  //调用方法
        if(addResult > 0){
            session.removeAttribute("fileName");
            map.put("result","success");
            map.put("msg","修改商品信息成功!");
        }else{
            map.put("result","error");
            map.put("masg","修改商品信息失败!");
        }
        return map;
    }

    /**
     * 删除商品
     * @param delIds
     * @return
     */
    @RequestMapping("/delProductInfo")
    public @ResponseBody Map<String, Object> delProductInfo(String delIds, HttpServletRequest req){
        Map<String, Object> resultMap = new HashMap<>();
        //设置上传文件的目标路径
        String path = req.getServletContext().getRealPath("/")+"upload/product/";
        try {
            //查出要删除的对象,首先删除这些对象存放的文件
            List<Product> products = productService.findProductInfoByIds(delIds);//查询出要删除的商品信息
            System.out.println(products);
            List<String> fileNames = new ArrayList<>();
            //循环取出所有对象的文件名
            for (Product item:products) {
                fileNames.add(item.getProductPicture());
                System.out.println(item.getProductPicture());
            }
            //调用删除多文件方法
            FileUploadUtil.delFileUpload(path, fileNames);
        }catch (Exception e){
            resultMap.put("result","error");
            resultMap.put("msg","删除商品图片异常!");
            e.printStackTrace();
            return resultMap;
        }
        //删除商品信息
        int delResult = productService.deleteProductInfo(delIds);
        if(delResult > 0){
            resultMap.put("result","success");
            resultMap.put("msg","删除商品成功!");
        }else{
            resultMap.put("result","error");
            resultMap.put("msg","删除商品失败!");
        }
        return resultMap;
    }

}
