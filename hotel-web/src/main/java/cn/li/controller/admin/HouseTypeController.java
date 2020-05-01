package cn.li.controller.admin;

import cn.li.pojo.HouseType;
import cn.li.service.HouseTypeService;
import cn.li.utils.FileUploadUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yajun
 * @create 2020/1/7
 *客房类型控制层
 */
@Controller
@RequestMapping("/main")
public class HouseTypeController {


    @Autowired
    private HouseTypeService houseTypeService;

    @GetMapping("/openHouseType")
    public String openHouseType(){
        return "house/houseTypeList";
    }



    /**
     * 获取客房类型数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getHouseType")
    public @ResponseBody
    Map<String,Object> getHouseType(@RequestParam Integer page, @RequestParam Integer limit){
        Map<String,Object> map = new HashMap<>();
        try {
            PageInfo pageInfo = houseTypeService.getAllHouseType(page,limit);
            map.put("count",houseTypeService.getHouseTypeCount());
            map.put("data",pageInfo.getList());
            map.put("msg","成功");
            map.put("code","0");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }




    /**
     * 打开添加客房页面
     * @return
     */
    @RequestMapping("/openHouseTypeHtml")
    public String openHouseTypeHtml(Model model){
        model.addAttribute("type","houseType");
        return "house/addHouseView";
    }





    /**
     * 添加客房类型
     * @param houseType
     * @param session
     * @param req
     * @return
     */
    @PostMapping("/addHouseType")
    public @ResponseBody
    Map<String,Object> addHouseType(HouseType houseType, HttpSession session, HttpServletRequest req) {
        Map<String, Object> returnMap = new HashMap<>();
        String fileName = (String) session.getAttribute("fileName");//取出存入的图片名
        if (fileName != null && !"".equals(fileName)) {
            houseType.setHouseImg(fileName);
        }
        houseType.setPrice(houseType.getPrice()*100);
        int addHouseType = houseTypeService.insert(houseType);
        if (addHouseType > 0) {
            returnMap.put("result", "success");
            returnMap.put("msg","添加客房类型成功！");
            session.removeAttribute("fileName");
        } else{
            returnMap.put("result","error");
            returnMap.put("msg","添加客房类型失败！");
        }
        return returnMap;
    }





    /**
     * 添加客房图片
     * @param file
     * @param modelMap
     * @param req
     * @return
     */
    @PostMapping("/addHouseTypeImg")
    public  @ResponseBody
    Map<String,Object> addHouseTypeImg(MultipartFile file, ModelMap modelMap, HttpServletRequest req){
        Map<String, Object> returnMap = new HashMap<>();
        //文件上传路径
        String path = req.getServletContext().getRealPath("/")+"upload/house/";
        String name = (String) req.getSession().getAttribute("fileName");
        if (name!=null){
            FileUploadUtil.delFileUpload(path+name);
        }
        try{
            String fileName = FileUploadUtil.fileUpload(file,path);
            System.out.println(fileName);
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
     * 删除客房类型
     * @param id
     * @return
     */
    @RequestMapping("/delHouseType")
    public @ResponseBody
    Map<String,Object> delHouseType(String id){
        Map<String, Object> returnMap = new HashMap<>();
        try {
            int i = houseTypeService.deleteByPrimaryKey(id);
            if (i>0){
                returnMap.put("result","success");
                returnMap.put("msg","删除客房类型成功！");
            }else {
                returnMap.put("result","error");
                returnMap.put("msg","删除客房类型失败！");
            }
        }catch (Exception e){
            returnMap.put("result","error");
            returnMap.put("msg","删除客房类型出现异常！");
            e.printStackTrace();
        }
        return returnMap;
    }





    /**
     * 打开修改客房类型页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/openUpdateHouseTypeHtml")
    public String updateHouseType(Integer id, Model model, HttpSession session){
        HouseType houseType = houseTypeService.selectByPrimaryKey(id);
        model.addAttribute("houseType",houseType);
        session.setAttribute("fileName",houseType.getHouseImg());
        model.addAttribute("type","houseType");
        return "house/updateHouseView";
    }




    /**
     * 修改客房类型
     * @param houseType
     * @return
     */
    @PostMapping("/updateHouseType")
    public @ResponseBody
    Map<String,Object> updateHouseType(HouseType houseType,HttpSession session){
        Map<String, Object> returnMap = new HashMap<>();
        String fileName = (String) session.getAttribute("fileName");//取出存入的图片名
        if (fileName != null && !"".equals(fileName)) {
            houseType.setHouseImg(fileName);
        }
        try {
            houseType.setPrice(houseType.getPrice()*100);
            int i = houseTypeService.updateByPrimaryKeySelective(houseType);
            if (i>0){
                session.removeAttribute("fileName");
                returnMap.put("result","success");
                returnMap.put("msg","修改客房类型成功！");
            }else {
                returnMap.put("result","error");
                returnMap.put("msg","修改客房类型失败！");
            }
        }catch (Exception e){
            returnMap.put("result","error");
            returnMap.put("msg","修改客房类型出现异常！");
            e.printStackTrace();
        }
        return returnMap;
    }
}
