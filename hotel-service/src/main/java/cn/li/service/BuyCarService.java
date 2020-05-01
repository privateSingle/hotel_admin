package cn.li.service;

import cn.li.mapper.BuyCarMapper;
import cn.li.pojo.BuyCar;
import cn.li.pojo.DiAndPr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyCarService implements BuyCarMapper {

    @Autowired
    private BuyCarMapper buyCarMapper;

    @Override
    public List<BuyCar> findAllBuyCar(Integer clientId) {
        return buyCarMapper.findAllBuyCar(clientId);
    }

    @Override
    public BuyCar findBuyCar(Integer proId,Integer type,Integer clientId) {
        return buyCarMapper.findBuyCar(proId, type,clientId);
    }

    @Override
    public Integer addBuyCar(BuyCar buyCar) {
        return buyCarMapper.addBuyCar(buyCar);
    }

    @Override
    public Integer updateBuyCar(BuyCar buyCar) {
        return buyCarMapper.updateBuyCar(buyCar);
    }

    @Override
    public Integer deleteBuyCarById(Integer id) {
        return buyCarMapper.deleteBuyCarById(id);
    }

    @Override
    public List<String> findHouseCodeBuyClientId(Integer clientId) {
        return buyCarMapper.findHouseCodeBuyClientId(clientId);
    }

    @Override
    public Integer deleteBuyCarByClientId(Integer clientId) {
        return buyCarMapper.deleteBuyCarByClientId(clientId);
    }
}
