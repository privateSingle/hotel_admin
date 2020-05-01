package cn.li.mapper;

import cn.li.pojo.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface HouseMapper {
    int insert(House record);

    int insertSelective(House record);

    /**
     *获取所有客房
     * @return
     */
    List<House> getHouseList();

    /**
     * 修改客房状态
     * @param house
     * @return
     */
    int updateHouseStatus(House house);

    /**
     * 根据条件查询客房集合
     * @param houseCode 客房号
     * @param houseType 客房类型
     * @param houseStatus 客房状态
     * @return
     */
    List<House> getHouseListByCode(@Param("houseCode") String houseCode,@Param("houseType") Integer houseType,
                                   @Param("houseStatus") Integer houseStatus);

    /**
     * 获取客房数量
     * @return
     */
    Integer getHouseCount();

    /**
     * 根据客房好删除客房
     * @param houseCode
     * @return
     */
    Integer deleteHouseByCode(@Param("houseCode") String houseCode);

    House getHouseByHouseCode(@Param("houseCode") String houseCode);
}