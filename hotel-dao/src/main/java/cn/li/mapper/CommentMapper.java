package cn.li.mapper;

import cn.li.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface CommentMapper {

    /**
     * 根据ID删除一条评价信息
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(Integer id);

    /**
     * 插入一条评价信息
     * @param record
     * @return
     */
    int insert(Comment record);

    /**
     * 选择性插入一条评价
     * @param record
     * @return
     */
    int insertSelective(Comment record);

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Comment record);

    /**
     * 分页查询
     * @param start
     * @param limit
     * @param clientId
     * @return
     */
    List<Comment> selectAllByPage(@Param("start") Integer start, @Param("limit") Integer limit, @Param("clientId") Integer clientId);

    /**
     * 查询总记录数
     * @param clientId
     * @return
     */
    Integer selectAllCountByClientId(@Param("clientId") Integer clientId);

    /**
     * 查询所有
     * @return
     */
    List<Comment> selectComments(@Param("houseTypeId") Integer houseTypeId);

}