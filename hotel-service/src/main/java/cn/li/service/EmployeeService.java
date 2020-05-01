package cn.li.service;

import cn.li.pojo.Client;
import cn.li.pojo.SearchPojo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.pojo.Employee;
import cn.li.mapper.EmployeeMapper;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 删除员工信息
     * @param ids
     * @return
     */
    public int deleteByPrimaryKey(String ids) {
        return employeeMapper.deleteByPrimaryKey(ids);
    }

    /**
     * 插入一条员工信息
     * @param record
     * @return
     */
    public int insert(Employee record) {
        return employeeMapper.insert(record);
    }

    /**
     * 选择性插入
     * @param record
     * @return
     */
    public int insertSelective(Employee record) {
        return employeeMapper.insertSelective(record);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public Employee selectByPrimaryKey(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键选择性修改
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(Employee record) {
        return employeeMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键修改
      * @param record
     * @return
     */
    public int updateByPrimaryKey(Employee record) {
        return employeeMapper.updateByPrimaryKey(record);
    }

    /**
     * 初始化查询
     * @param page
     * @param limit
     * @return
     */
     public List<Employee> selectEmployeeAllInfo(Integer page, Integer limit) {

         //利用PageHelper分页工具
         PageHelper.startPage(page, limit);
         List<Employee> employeeList = employeeMapper.selectEmployeeAllInfo();
         PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
         //返回查询出的集合
         return pageInfo.getList();

     }

    /**
     * 查询总记录数
     * @return
     */
    public Integer selectEmployeeCountAll() {

        return employeeMapper.selectEmployeeCountAll();

    }

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    public Employee selectEmployeeByNameAndPwd(String loginName, String password) {
        return employeeMapper.selectEmployeeByNameAndPwd(loginName, password);
    }
}
