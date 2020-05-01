package cn.li.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.ProductMapper;
import cn.li.pojo.Product;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class ProductService{

    @Autowired
    private ProductMapper productMapper;

    public int deleteByPrimaryKey(Integer id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    public int insert(Product record) {
        return productMapper.insert(record);
    }

    public int insertSelective(Product record) {
        return productMapper.insertSelective(record);
    }
    
    public Product selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
    
    public int updateByPrimaryKeySelective(Product record) {
        return productMapper.updateByPrimaryKeySelective(record);
    }
    
    public int updateByPrimaryKey(Product record) {

        return productMapper.updateByPrimaryKey(record);
    }

    public PageInfo<Product> findAllProduct(Integer curPage, Integer pageSize,Product product){
        PageHelper.startPage(curPage,pageSize);
        List<Product> list = productMapper.finAllProduct(product);
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<Product> findAllProduct(Product product){
        return productMapper.finAllProduct(product);
    }

    public int findAllProductCount(Product product){
        return productMapper.findAllProductCount(product);
    }

    public int deleteProductInfo(String ids){
        return productMapper.deleteProductInfo(ids);
    }

    public List<Product> findProductInfoByIds(String ids){
        return productMapper.findProductInfoByIds(ids);
    }

    public int editProductCount(Integer id,Integer count){
        return productMapper.editProductCount(id, count);
    }
    public List<Product> findProductByProductIds(String ids){
        return productMapper.findProductByProductIds(ids);
    }

    public Integer updateProductCountById(Integer id,Integer count){
        return productMapper.updateProductCountById(id,count);
    }
}
