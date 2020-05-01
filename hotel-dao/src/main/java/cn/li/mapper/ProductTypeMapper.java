package cn.li.mapper;

import cn.li.pojo.Product;
import cn.li.pojo.ProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    ProductType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);

    List<ProductType> findAllProductType();

    int findAllProductTypeCount();

    int delProductTypeInfo(@Param("Ids") String Ids);
}