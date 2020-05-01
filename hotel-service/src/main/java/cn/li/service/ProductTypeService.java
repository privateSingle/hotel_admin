package cn.li.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.ProductTypeMapper;
import cn.li.pojo.ProductType;

import java.util.List;

@Service
public class ProductTypeService{

    @Autowired
    private ProductTypeMapper productTypeMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return productTypeMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(ProductType record) {
        return productTypeMapper.insert(record);
    }

    
    public int insertSelective(ProductType record) {
        return productTypeMapper.insertSelective(record);
    }

    
    public ProductType selectByPrimaryKey(Integer id) {
        return productTypeMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(ProductType record) {
        return productTypeMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(ProductType record) {
        return productTypeMapper.updateByPrimaryKey(record);
    }

    public List<ProductType> findAllProductType(){
        return productTypeMapper.findAllProductType();
    }

    public PageInfo<ProductType> findAllProductType(Integer curPage, Integer pageSize){
        PageHelper.startPage(curPage,pageSize);
        List<ProductType> list = productTypeMapper.findAllProductType();
        PageInfo<ProductType> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    public int findAllProductTypeCount(){
        return productTypeMapper.findAllProductTypeCount();
    }

    public int delProductTypeInfo(String Ids){
        return productTypeMapper.delProductTypeInfo(Ids);
    }
}
