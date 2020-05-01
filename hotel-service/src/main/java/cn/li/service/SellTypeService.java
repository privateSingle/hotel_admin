package cn.li.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.SellTypeMapper;
import cn.li.pojo.SellType;
 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class SellTypeService{

    @Autowired
    private SellTypeMapper sellTypeMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return sellTypeMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(SellType record) {
        return sellTypeMapper.insert(record);
    }

    
    public int insertSelective(SellType record) {
        return sellTypeMapper.insertSelective(record);
    }

    
    public SellType selectByPrimaryKey(Integer id) {
        return sellTypeMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SellType record) {
        return sellTypeMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(SellType record) {
        return sellTypeMapper.updateByPrimaryKey(record);
    }

}
