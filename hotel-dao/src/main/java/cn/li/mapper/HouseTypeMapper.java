package cn.li.mapper;

import cn.li.pojo.HouseType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: null
 */
public interface HouseTypeMapper {
    int deleteByPrimaryKey(@Param("id") String id);

    int insert(HouseType record);

    int insertSelective(HouseType record);

    HouseType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HouseType record);

    int updateByPrimaryKey(HouseType record);

    List<HouseType> selectAllHouseType();

    Integer getHouseTypeCount();

    /**
     * 修改客房数量
     *
     * @return
     */
    int updateHouseTypeCount(@Param("count") Integer count, @Param("id") Integer id);


    int updateHouseTypeRemain(@Param("id") Integer id, @Param("type") Integer type);

    /**
     * 根据条件修改客房类型的客房数量
     *
     * @param id   客房类型id
     * @param type 修改类型 0 扣除客房数量 1 添加客房数量
     * @return
     */
    int updateHouseType(@Param("id") Integer id, @Param("type") Integer type);

    int updateHouseRemain(@Param("id") Integer id,@Param("remain")Integer reamin);
}