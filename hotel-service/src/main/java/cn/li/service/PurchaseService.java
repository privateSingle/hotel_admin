package cn.li.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.pojo.Purchase;
import cn.li.mapper.PurchaseMapper;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class PurchaseService{

    @Autowired
    private PurchaseMapper purchaseMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return purchaseMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Purchase record) {
        return purchaseMapper.insert(record);
    }

    
    public int insertSelective(Purchase record) {
        return purchaseMapper.insertSelective(record);
    }

    
    public Purchase selectByPrimaryKey(Integer id) {
        return purchaseMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Purchase record) {
        return purchaseMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Purchase record) {
        return purchaseMapper.updateByPrimaryKey(record);
    }

    public PageInfo<Purchase> findAllPurchase(Integer curPage, Integer pageSize,String name,String time){
        PageHelper.startPage(curPage,pageSize);
        List<Purchase> list = purchaseMapper.findAllPurchase(name,time);
        PageInfo<Purchase> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    public int findAllPurchaseCount(String name,String time){
        return purchaseMapper.findAllPurchaseCount(name, time);
    }

    public int delPurchaseInfo(String Ids){
        return purchaseMapper.delPurchaseInfo(Ids);
    }
}
