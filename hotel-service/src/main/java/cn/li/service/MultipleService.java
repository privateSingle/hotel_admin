package cn.li.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.pojo.Multiple;
import cn.li.mapper.MultipleMapper;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: 连锁店表业务层
  */
@Service
public class MultipleService{

    @Autowired
    private MultipleMapper multipleMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return multipleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 插入一条连锁店信息
     * @param record
     * @return
     */
    public Integer insert(Multiple record) {
        return multipleMapper.insert(record);
    }

    /**
     * 选择性插入一条连锁店信息
     * @param record
     * @return
     */
    public Integer insertSelective(Multiple record) {
        return multipleMapper.insertSelective(record);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public Multiple selectByPrimaryKey(Integer id) {
        return multipleMapper.selectByPrimaryKey(id);
    }

    /**
     * 选择性修改分店信息
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(Multiple record) {
        return multipleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 修改分店信息
     * @param record
     * @return
     */
    public Integer updateByPrimaryKey(Multiple record) {
        return multipleMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询全部连锁店信息
     * @param page
     * @param limit
     * @return
     */
    public List<Multiple> selectAllInfo(Integer page, Integer limit){

        //使用PageHelper分页插件
        PageHelper.startPage(page, limit);
        List<Multiple> multiples = multipleMapper.selectAllInfo();
        PageInfo<Multiple> pageInfo = new PageInfo<>(multiples);
        return pageInfo.getList();

    }

    /**
     * 查询总记录数
     * @return
     */
    public Integer selectAllCount() {
        return multipleMapper.selectAllCount();
    }

    /**
     * 删除一条或多条连锁店信息
     * @param ids
     * @return
     */
    public Integer deleteMultipleInfo(String ids) {
        return multipleMapper.deleteMultipleInfo(ids);
    }
}
