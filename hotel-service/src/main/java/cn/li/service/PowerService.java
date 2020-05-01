package cn.li.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.PowerMapper;
import cn.li.pojo.Power;
 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class PowerService{

    @Autowired
    private PowerMapper powerMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return powerMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Power record) {
        return powerMapper.insert(record);
    }

    
    public int insertSelective(Power record) {
        return powerMapper.insertSelective(record);
    }

    
    public Power selectByPrimaryKey(Integer id) {
        return powerMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Power record) {
        return powerMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Power record) {
        return powerMapper.updateByPrimaryKey(record);
    }

}
