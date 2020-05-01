package cn.li.mapper;

import cn.li.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: null
 */
@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 查询全部商品信息
     *
     * @return
     */
    List<Product> finAllProduct(Product product);

    /**
     * 查询全部商品信息记录数
     *
     * @return
     */
    int findAllProductCount(Product product);

    /**
     * 删除商品信息
     *
     * @param ids
     * @return
     */
    int deleteProductInfo(@Param("ids") String ids);

    /**
     * 根据ID查询商品信息
     * @param ids
     * @return
     */
    List<Product> findProductInfoByIds(@Param("ids") String ids);

    /**
     * 修改商品的库存数量
     * @return
     */
    int editProductCount(@Param("id") Integer id,@Param("count") Integer count);

    List<Product> findProductByProductIds(@Param("ids") String ids);

    Integer updateProductCountById(@Param("id") Integer id,@Param("count") Integer count);
}