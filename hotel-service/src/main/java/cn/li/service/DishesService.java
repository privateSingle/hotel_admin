package cn.li.service;

import cn.li.pojo.DiAndPr;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.DishesMapper;
import cn.li.pojo.Dishes;

import java.util.List;

@Service
public class DishesService{

    @Autowired
    private DishesMapper dishesMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return dishesMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Dishes record) {
        return dishesMapper.insert(record);
    }

    
    public int insertSelective(Dishes record) {
        return dishesMapper.insertSelective(record);
    }

    
    public Dishes selectByPrimaryKey(Integer id) {
        return dishesMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Dishes record) {
        return dishesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Dishes record) {
        return dishesMapper.updateByPrimaryKey(record);
    }

    public PageInfo<Dishes> findAllDishesInfo(Integer curPage, Integer pageSize,String name){
        PageHelper.startPage(curPage,pageSize);
        List<Dishes> list = dishesMapper.findAllDishesInfo(name);
        PageInfo<Dishes> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<Dishes> findAllDishesInfo(String name){
        return dishesMapper.findAllDishesInfo(name);
    }

    public int findAllDishesCount(String name){
        return dishesMapper.findAllDishesCount(name);
    }

    public int delDishesInfo(String Ids){
        return dishesMapper.delDishesInfo(Ids);
    }

    public List<Dishes> findDishesInfoByIds(String Ids){
        return dishesMapper.findDishesInfoByIds(Ids);
    }
    public List<DiAndPr> findAll(String name){
        return dishesMapper.findAll(name);
    }
}
