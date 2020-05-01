package cn.li.mapper;

import cn.li.pojo.Purchase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface PurchaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Purchase record);

    int insertSelective(Purchase record);

    Purchase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    /**
     * 查询所有采购信息
     * @return
     */
    List<Purchase> findAllPurchase(@Param("name") String name,@Param("time") String time);

    /**
     * 查询所有采购信息记录数
     * @return
     */
    int findAllPurchaseCount(@Param("name") String name,@Param("time") String time);

    /**
     * 删除采购信息
     * @param Ids
     * @return
     */
    int delPurchaseInfo(@Param("Ids") String Ids);


}