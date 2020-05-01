package cn.li.service;

import cn.li.mapper.HouseTypeMapper;
import cn.li.mapper.OrderHouseDetailMapper;
import cn.li.pojo.HouseType;
import cn.li.pojo.OrderHouseDetail;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.HouseMapper;
import cn.li.pojo.House;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class HouseService{

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private OrderHouseDetailMapper houseDetailMapper;

    @Autowired
    private HouseTypeMapper houseTypeMapper;

    /**
     * 添加客房信息
     * @param record
     * @return
     */
    @Transactional
    public Map<String, Object> insert(House record) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            House house = houseMapper.getHouseByHouseCode(record.getHouseCode());
            if (house==null){
                int insert = houseMapper.insert(record);
                int i = houseTypeMapper.updateHouseType(record.getTypeId(),1);
                if (insert>0 && i>0){
                    resultMap.put("result", "success");
                    resultMap.put("msg", "添加成功！");
                }else {
                    resultMap.put("result", "error");
                    resultMap.put("msg", "添加失败！");
                }
            }else {
                resultMap.put("result", "error");
                resultMap.put("msg", "该客房号已存在！");
            }
        }catch (Exception e){
            resultMap.put("result", "error");
            resultMap.put("msg", "添加出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }

    
    public int insertSelective(House record) {
        return houseMapper.insertSelective(record);
    }


    /**
     * 获取所有客房信息
     * @return
     */
    public List<House> getHouseList(){
        return houseMapper.getHouseList();
    }


    public PageInfo<House> getHouseListByCode(Integer page,Integer limit,
                                              String houseCode, Integer houseType, Integer HouseStatus){
        PageInfo <House> pageInfo = null;
        try {
            Page<House> page1 = PageHelper.startPage(page,limit);
            pageInfo = new PageInfo<>(houseMapper.getHouseListByCode(houseCode,houseType,HouseStatus));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * 获取客房数量
     * @return
     */
    public Integer getHouseCount(){
       return houseMapper.getHouseCount();
    }

    /**
     * 根据客房号删除客房
     * @param houseCode
     * @return
     */
    @Transactional
    public Map<String, Object> deleteHouseByCode(String houseCode,String houseTypeId) {
        //
        Map<Integer,Long> typeIdMap = getMap(houseTypeId);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            typeIdMap.forEach((k,v)->{
               houseTypeMapper.updateHouseTypeCount(v.intValue(),k);
            });
            Integer house = houseMapper.deleteHouseByCode(houseCode);

            HouseType houseType = new HouseType();
            if(house > 0) {
                resultMap.put("result", "success");
                resultMap.put("msg", "删除成功！");
            }else{
                resultMap.put("result", "error");
                resultMap.put("msg", "删除失败！");
            }
        }catch (Exception e){
            resultMap.put("result", "error");
            resultMap.put("msg", "删除出现异常！");
            e.printStackTrace();
        }
        return resultMap;
    }



    public Map<Integer, Long> getMap(String data){
        String[] d = data.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < d.length; i++) {
            list.add(Integer.parseInt(d[i]));
        }
        Map<Integer, Long> map = list.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));
        return map;
    }
}
