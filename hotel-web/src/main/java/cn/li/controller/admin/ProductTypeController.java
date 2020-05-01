package cn.li.controller.admin;

import cn.li.pojo.Product;
import cn.li.pojo.ProductType;
import cn.li.service.ProductService;
import cn.li.service.ProductTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Wang
 * 商品类型表
 */
@Controller
@RequestMapping("/main")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    /**
     * 打开商品类型页面
     * @return
     */
    @RequestMapping("/openProductType")
    public String openProductType(){
        return "product/productTypeList";
    }
    /**
     * 初始化页面
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("productType/initData")
    public @ResponseBody
    Map<String, Object> initData(Integer limit, Integer page){
        PageInfo<ProductType> selectResult = productTypeService.findAllProductType(page,limit);
        List<ProductType> listData = selectResult.getList();
        Map<String, Object> result = new HashMap<>();
        Integer count = productTypeService.findAllProductTypeCount();
        result.put("msg","");
        result.put("count", count);
        result.put("code","0");
        result.put("data",listData);
        return result;
    }

    /**
     * 打开新增商品类型窗口
     * @return
     */
    @RequestMapping("operation/addPetTypeInfoWindow")
    public String skipAddProductInfoView(Model model){
        model.addAttribute("operationType","productType");
        return "operation/addView";
    }

    /**
     * 添加商品类型
     * @param productType
     * @return
     */
    @RequestMapping("operation/addProductTypeInfo")
    public @ResponseBody
    Map<String,Object> addProductType(ProductType productType){
        Map<String,Object> map = new HashMap<>();
        int count = productTypeService.insert(productType);
        if (count > 0){
            map.put("result","success");
            map.put("msg","添加商品类型成功!");
        }else{
            map.put("result","error");
            map.put("msg","添加商品类型失败!");
        }
        return map;
    }

    /**
     * 修改商品类型
     * @param productType
     * @return
     */
    @RequestMapping("productType/editProductTypeInfo")
    public @ResponseBody
    Map<String,Object> updateProductType(ProductType productType){
        Map<String,Object> map = new HashMap<>();
        int count = productTypeService.updateByPrimaryKey(productType);
        if (count > 0){
            map.put("result","success");
            map.put("msg","修改商品类型成功!");
        }else{
            map.put("result","error");
            map.put("msg","修改商品类型失败!");
        }
        return map;
    }

    /**
     * 删除商品类型
     * @param delIds
     * @return
     */
    @RequestMapping("/delProductTypeInfo")
    public @ResponseBody
    Map<String,Object> delProductType(String delIds){
        Map<String,Object> map = new HashMap<>();
        List<Product> list = productService.findProductByProductIds(delIds);
        if (list.size() == 0){
            int count = productTypeService.delProductTypeInfo(delIds);
            if(count > 0){
                map.put("result","success");
                map.put("msg","删除商品成功!");
            }else{
                map.put("result","error");
                map.put("msg","删除商品失败!");
            }
        }else{
            map.put("result","no");
            map.put("msg","该类型下存在商品,请删除商品后重试.....");
        }
        return map;
    }
}
