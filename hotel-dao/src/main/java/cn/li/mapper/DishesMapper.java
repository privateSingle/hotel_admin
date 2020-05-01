package cn.li.mapper;

import cn.li.pojo.DiAndPr;
import cn.li.pojo.Dishes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dishes record);

    int insertSelective(Dishes record);

    Dishes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dishes record);

    int updateByPrimaryKey(Dishes record);

    /**
     * 查询所有菜品信息
     * @return
     */
    List<Dishes> findAllDishesInfo(@Param("name") String name);

    /**
     * 查询所有菜品记录数
     * @return
     */
    int findAllDishesCount(@Param("name") String name);

    /**
     * 根据ID查询所有菜品信息
     * @param Ids
     * @return
     */
    List<Dishes> findDishesInfoByIds(@Param("Ids") String Ids);

    /**
     * 删除菜品信息
     * @param Ids
     * @return
     */
    int delDishesInfo(@Param("Ids") String Ids);

    /**
     * 查询菜品和商品部分数据
     * @return
     */
    List<DiAndPr> findAll(@Param("name") String name);
}