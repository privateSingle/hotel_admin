package cn.li.controller.admin;

import cn.li.pojo.House;
import cn.li.pojo.HouseType;
import cn.li.service.HouseService;
import cn.li.service.HouseTypeService;
import cn.li.service.OrderHouseDetailService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yajun
 * @create 2020/1/7
 * 客房表控制层
 */
@Controller
@RequestMapping("/main")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private OrderHouseDetailService orderHouseDetailService;

    @Autowired
    private HouseTypeService houseTypeService;

    /**
     * 客房页面跳转
     * @return
     */
    @GetMapping("/openHouse")
    public String openHouse(Model model){
        List<HouseType> houseTypes = houseTypeService.selectAllHouseType();
        model.addAttribute("houseTypes",houseTypes);
        return "house/houseList";
    }


    /**
     * 获取客房集合
     * @param page
     * @param limit
     * @param houseCode
     * @param houseType
     * @param houseStatus
     * @return
     */
    @GetMapping("/getHouseList")
    public @ResponseBody
    Map<String,Object> getHouseList(@RequestParam Integer page,@RequestParam Integer limit,
                                    String houseCode,Integer houseType,Integer houseStatus){
        Map<String, Object> mapResult = new HashMap<>();
        try {
            PageInfo pageInfo  = houseService.getHouseListByCode(page,limit,houseCode,houseType,houseStatus);
            mapResult.put("count",houseService.getHouseCount());
            mapResult.put("data",pageInfo.getList());
            mapResult.put("msg","成功");
            mapResult.put("code","0");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapResult;
    }



    /**
     * 删除客房
     * @param houseCode
     * @return
     */
    @GetMapping("/deleteHouse")
    public @ResponseBody
    Map<String, Object> deleteHouse(String houseCode,String houseTypeId){
        Map<String, Object> resultMap = houseService.deleteHouseByCode(houseCode,houseTypeId);
        return resultMap;
    }


    /**
     * 打开客房添加页面
     * @param model
     * @return
     */
    @RequestMapping("/openAddHouseInfoHtml")
    public String openHouseInfo(Model model){
        List<HouseType>  houseTypes = houseTypeService.selectAllHouseType();
        model.addAttribute("type","house");
        model.addAttribute("houseTypes",houseTypes);
        return "house/addHouseView";
    }

    /**
     * 添加客房
     * @return
     */
    @PostMapping("/addHouseInfo")
    public @ResponseBody
    Map<String, Object> addHouseInfo(House house){
        house.setHouseStatus(0);
        Map<String, Object> resultMap = houseService.insert(house);
        return resultMap;
    }
}

