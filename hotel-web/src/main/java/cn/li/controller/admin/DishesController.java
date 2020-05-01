package cn.li.controller.admin;

import cn.li.pojo.Dishes;
import cn.li.service.DishesService;
import cn.li.utils.FileUploadUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class DishesController {

    @Autowired
    private DishesService dishesService;

    /**
     * 跳转到菜品页面
     * @return
     */
    @RequestMapping("/openDishes")
    public String openDishes(){
        return "dishes/dishesList";
    }

    @RequestMapping("dishes/initData")
    public @ResponseBody
    Map<String,Object> initData(Integer limit, Integer page, String name){
        Map<String,Object> result = new HashMap<>();
        PageInfo<Dishes> selectResult = dishesService.findAllDishesInfo(page,limit,name);
        List<Dishes> listData = selectResult.getList();
        Integer count = dishesService.findAllDishesCount(name);
        result.put("msg","");
        result.put("count", count);
        result.put("code","0");
        result.put("data",listData);
        return result;
    }

    /**
     * 打开添加菜品信息图片
     * @param model
     * @return
     */
    @RequestMapping("operation/addDishesInfoWindow")
    public String skipAddDishesInfoView(Model model){
        model.addAttribute("operationType","dishes");
        return "operation/addView";
    }

    @RequestMapping("/operation/addDishes")
    public @ResponseBody
    Map<String,Object> addDishesInfo(HttpSession session,Dishes dishes){
        Map<String,Object> map = new HashMap<>();
        String fileName = (String)session.getAttribute("fileName");//取出存入的图片名
        if (fileName!=null&&!"".equals(fileName)){
            dishes.setPicture(fileName);
        }
        int addResult = dishesService.insert(dishes);  //调用方法
        if(addResult > 0){
            session.removeAttribute("fileName");
            map.put("result","success");
            map.put("msg","添加菜品信息成功!");
        }else{
            map.put("result","error");
            map.put("msg","添加菜品信息失败!");
        }
        return map;
    }

    /**
     * 打开修改商品窗口
     * @param result
     * @return
     */
    @RequestMapping("operation/updateDishesInfoWindow/{result}")
    public String skipUpdateInfoView(@PathVariable("result") Integer result, Model model, HttpSession session){
        Dishes dishes = dishesService.selectByPrimaryKey(result);//查询出要修改的菜品信息
        model.addAttribute("dishesList",dishes);
        //重新存入旧图片名,以防未修改图片直接提交导致数据库图片为null
        session.setAttribute("fileName",dishes.getPicture());
        model.addAttribute("operationType","dishes");
        return "operation/updateProductView";
    }

    /**
     * 上传修改菜品图片
     * @param file
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping("operation/updateDishesInfoWindow/uploads")
    public @ResponseBody
    Map<String,Object> uploadUpdateImage(MultipartFile file, HttpServletRequest req) throws IOException{
        Map<String,Object> map = ProductController.uploadImage(file,req);
        return map;
    }

    /**
     * 修改商品信息
     * @param dishes
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/operation/updateDishesInfoWindow/updateDishes")
    public @ResponseBody
    Map<String,Object> updateDishes(Dishes dishes, HttpSession session) throws IOException{
        Map<String,Object> map = new HashMap<>();
        String fileName = (String)session.getAttribute("fileName");//取出存入的图片名
        if(fileName != null && fileName != ""){
            dishes.setPicture(fileName);
        }
        int addResult = dishesService.updateByPrimaryKey(dishes);  //调用方法
        if(addResult > 0){
            session.removeAttribute("fileName");
            map.put("result","success");
            map.put("msg","修改菜品信息成功!");
        }else{
            map.put("result","error");
            map.put("masg","修改菜品信息失败!");
        }
        return map;
    }

    /**
     * 删除商品
     * @param delIds
     * @return
     */
    @RequestMapping("/delDishesInfo")
    public @ResponseBody Map<String, Object> delDishesInfo(String delIds, HttpServletRequest req){
        Map<String, Object> resultMap = new HashMap<>();
        //设置上传文件的目标路径
        String path = req.getServletContext().getRealPath("/")+"upload/product/";
        try {
            //查出要删除的对象,首先删除这些对象存放的文件
            List<Dishes> dishes = dishesService.findDishesInfoByIds(delIds);//查询出要删除的商品信息
            List<String> fileNames = new ArrayList<>();
            //循环取出所有对象的文件名
            for (Dishes item:dishes) {
                fileNames.add(item.getPicture());
            }
            //调用删除多文件方法
            FileUploadUtil.delFileUpload(path, fileNames);
        }catch (Exception e){
            resultMap.put("result","error");
            resultMap.put("msg","删除菜品图片异常!");
            e.printStackTrace();
            return resultMap;
        }
        //删除菜品信息
        int delResult = dishesService.delDishesInfo(delIds);
        if(delResult > 0){
            resultMap.put("result","success");
            resultMap.put("msg","删除菜品成功!");
        }else{
            resultMap.put("result","error");
            resultMap.put("msg","删除菜品失败!");
        }
        return resultMap;
    }
}
