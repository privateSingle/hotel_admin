package cn.li.mapper;

import cn.li.pojo.Multiple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: 连锁店表Mapper
  */
public interface MultipleMapper {

    /**
     * 根据id删除单条信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入一条信息
     * @param record
     * @return
     */
    Integer insert(Multiple record);

    /**
     * 选择性插入一条信息
     * @param record
     * @return
     */
    Integer insertSelective(Multiple record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Multiple selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record
     * @return
     */
    Integer updateByPrimaryKeySelective(Multiple record);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    Integer updateByPrimaryKey(Multiple record);

    /**
     * 查询全部方法
     * @return
     */
    List<Multiple> selectAllInfo();

    /**
     * 查询总记录数
     * @return
     */
    Integer selectAllCount();

    /**
     * 删除一条或多条连锁店信息
     * @param ids
     * @return
     */
    Integer deleteMultipleInfo(@Param("ids") String ids);
}