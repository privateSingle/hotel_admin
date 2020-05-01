package cn.li.mapper;

import cn.li.pojo.SellType;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface SellTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SellType record);

    int insertSelective(SellType record);

    SellType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SellType record);

    int updateByPrimaryKey(SellType record);
}