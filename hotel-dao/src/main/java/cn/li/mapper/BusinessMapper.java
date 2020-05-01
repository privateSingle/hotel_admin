package cn.li.mapper;

import cn.li.pojo.Business;
import cn.li.pojo.SearchPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface BusinessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Business record);

    int insertSelective(Business record);

    Business selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Business record);

    int updateByPrimaryKey(Business record);

    /**
     * 查询全部信息
     * @return
     */
    List<Business> selectAllInfo(SearchPojo searchPojo);

    /**
     * 查询总记录数
     * @param searchPojo
     * @return
     */
    Integer selectAllCount(SearchPojo searchPojo);

    /**
     * 根据采购ID修改营业表信息
     */
    int updateBusinessByPurchaseId(@Param("id") Integer id, @Param("price") Integer price);

    /**
     * 根据采购ID删除营业表记录
     * @param ids
     * @return
     */
    Integer deleteByPurchaseId(@Param("ids") String ids);
}