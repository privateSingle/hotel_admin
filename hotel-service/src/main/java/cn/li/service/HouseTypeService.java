package cn.li.service;

import cn.li.pojo.House;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.pojo.HouseType;
import cn.li.mapper.HouseTypeMapper;

import java.util.List;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: null
 */
@Service
public class HouseTypeService {

    @Autowired
    private HouseTypeMapper houseTypeMapper;


    public int deleteByPrimaryKey(String id) {
        return houseTypeMapper.deleteByPrimaryKey(id);
    }


    public int insert(HouseType record) {
        return houseTypeMapper.insert(record);
    }


    public int insertSelective(HouseType record) {
        return houseTypeMapper.insertSelective(record);
    }


    public HouseType selectByPrimaryKey(Integer id) {
        return houseTypeMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(HouseType record) {
        return houseTypeMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(HouseType record) {
        return houseTypeMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询所有客房类型
     */
    public List<HouseType> selectAllHouseType() {
        return houseTypeMapper.selectAllHouseType();
    }


    public PageInfo<HouseType> getAllHouseType(Integer page, Integer limit) {
        PageInfo<HouseType> pageInfo = null;
        try {
            Page<HouseType> page1 = PageHelper.startPage(page, limit);
            pageInfo = new PageInfo<>(houseTypeMapper.selectAllHouseType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    /**
     * 获取客房类型数量
     *
     * @return
     */
    public Integer getHouseTypeCount() {
        return houseTypeMapper.getHouseTypeCount();
    }

    /**
     * 修改客房的数量
     *
     * @param count
     * @param id
     * @return
     */
    public Integer updateHouseTypeCount(Integer count, Integer id) {
        return houseTypeMapper.updateHouseTypeCount(count, id);
    }

}
