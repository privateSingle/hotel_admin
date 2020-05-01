package cn.li.mapper;

import cn.li.pojo.Client;
import cn.li.pojo.SearchPojo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface ClientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Client record);

    int insertSelective(Client record);

    Client selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Client record);

    Integer updateByPrimaryKey(Client record);

    /**
     * 查询全部信息
     * @return
     */
    List<Client> selectAllInfo(SearchPojo searchPojo);

    /**
     * 查询总条数
     * @return
     */
    Integer selectCountAll(SearchPojo searchPojo);

    /**
     * 删除客户信息
     * @param ids
     * @return
     */
    Integer deleteById(@Param("ids") String ids);

    /**
     * 用户前台登录验证
     * @param client
     * @return
     */
    Client loginVerifyByPassword(Client client);

    /**
     * 根据邮箱和用户名验证
     * @param client
     * @return
     */
    Client verifyEmailAndName(Client client);

    /**
     * 根据客户ID修改客户余额
     * @param clientId
     * @param money
     * @return
     */
    Integer orderSettlement(@Param("clientId") Integer clientId,@Param("money") Integer money);

    /**
     * 根据邮箱查询一个用户
     * @param email
     * @return
     */
    Integer selectClientInfoByEmail(@Param("email") String email);
}