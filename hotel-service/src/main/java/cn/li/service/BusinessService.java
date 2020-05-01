package cn.li.service;

import cn.li.pojo.SearchPojo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.BusinessMapper;
import cn.li.pojo.Business;
import java.util.List;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class BusinessService{

    @Autowired
    private BusinessMapper businessMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return businessMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Business record) {
        return businessMapper.insert(record);
    }

    
    public int insertSelective(Business record) {
        return businessMapper.insertSelective(record);
    }

    
    public Business selectByPrimaryKey(Integer id) {
        return businessMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Business record) {
        return businessMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Business record) {
        return businessMapper.updateByPrimaryKey(record);
    }

     /**
      * 查询总记录数
      * @return
      */
     public Integer selectAllCount(SearchPojo searchPojo) {
         return businessMapper.selectAllCount(searchPojo);
     }

     /**
      * 查询全部信息
      * @param page
      * @param limit
      * @return
      */
     public List<Business> selectAllInfo(Integer page, Integer limit, SearchPojo searchPojo) {

         //使用PageHelper分页插件
         PageHelper.startPage(page, limit);
         List<Business> businessList = businessMapper.selectAllInfo(searchPojo);
         PageInfo<Business> pageInfo = new PageInfo<>(businessList);
         return pageInfo.getList();
     }

     public int updateBusinessByPurchaseId(Integer id,Integer price){
         return businessMapper.updateBusinessByPurchaseId(id, price);
     }

     /**
      * 删除营业记录
      * @param delIds
      * @return
      */
     public Integer deleteByPurchaseId(String delIds) {
         return businessMapper.deleteByPurchaseId(delIds);
     }
 }
