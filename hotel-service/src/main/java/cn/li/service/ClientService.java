package cn.li.service;

import cn.li.pojo.SearchPojo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.ClientMapper;
import cn.li.pojo.Client;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class ClientService{

    /**
     * 用户类数据层
     */
    @Autowired
    private ClientMapper clientMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return clientMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Client record) {
        return clientMapper.insert(record);
    }

    
    public int insertSelective(Client record) {
        return clientMapper.insertSelective(record);
    }

    
    public Client selectByPrimaryKey(Integer id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    /**
     * 选择性修改客户信息
     * @param record
     * @return
     */
    public Integer updateByPrimaryKeySelective(Client record) {
        return clientMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键修改客户信息
     * @param record
     * @return
     */
    public Integer updateByPrimaryKey(Client record) {
        return clientMapper.updateByPrimaryKey(record);
    }

     /**
      * 初始化数据查询
      * @param page
      * @param limit
      */
     public List<Client> selectAllInfo(Integer page, Integer limit, SearchPojo searchPojo) {

         //利用PageHelper分页工具
         PageHelper.startPage(page, limit);
         List<Client> clientList = clientMapper.selectAllInfo(searchPojo);
         PageInfo<Client> pageInfo = new PageInfo<>(clientList);
         //返回查询出的集合
         return pageInfo.getList();

     }

    /**
     * 查询总条数
     * @return
     */
    public Integer selectCountAll(SearchPojo searchPojo) {

        return clientMapper.selectCountAll(searchPojo);

    }

    /**
     * 删除客户信息
     * @param ids
     * @return
     */
    public Integer deleteById(String ids) {

        return clientMapper.deleteById(ids);
    }

    /**
     * 前台用户登录
     * @param client
     * @return
     */
    public Client loginVerifyByPassword(Client client) {
        return clientMapper.loginVerifyByPassword(client);
    }

    /**
     * 验证邮箱和用户名是否匹配
     * @param client
     * @return
     */
    public Client verifyEmailAndName(Client client) {
        if(client.getEmail() != null && client.getName() != null){
            return clientMapper.verifyEmailAndName(client);
        }else{
            return null;
        }
    }

    /**
     * 根据客户ID修改余额
     * @param clientId
     * @param money
     * @return
     */
    public Integer orderSettlement(Integer clientId,Integer money){
        return clientMapper.orderSettlement(clientId, money);
    }

    /**
     * 根据邮箱查找一个用户
     * @param email
     * @return
     */
    public Integer selectClientInfoByEmail(String email) {
        return clientMapper.selectClientInfoByEmail(email);
    }
}
