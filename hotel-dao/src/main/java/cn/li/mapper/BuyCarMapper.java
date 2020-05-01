package cn.li.mapper;

import cn.li.pojo.BuyCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuyCarMapper {
    List<BuyCar> findAllBuyCar(@Param("clientId") Integer clientId);

    BuyCar findBuyCar(@Param("proId") Integer proId,@Param("type") Integer type,@Param("clientId") Integer clientId);

    Integer addBuyCar(BuyCar buyCar);

    Integer updateBuyCar(BuyCar buyCar);

    Integer deleteBuyCarById(@Param("id") Integer id);

    List<String> findHouseCodeBuyClientId(@Param("clientId") Integer clientId);

    Integer deleteBuyCarByClientId(@Param("clientId") Integer clientId);
}
