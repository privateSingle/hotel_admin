package cn.li.mapper;

import cn.li.pojo.Employee;
import cn.li.pojo.SearchPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
public interface EmployeeMapper {

    /**
     * 删除员工信息
     * @param ids
     * @return
     */
    int deleteByPrimaryKey(@Param("ids") String ids);

    /**
     * 插入一条员工信息
     * @param record
     * @return
     */
    int insert(Employee record);

    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(Employee record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Employee selectByPrimaryKey(Integer id);


    /**
     * 根据主键选择性修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * 根据主键修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employee record);

    /**
     * 初始化查询
     * @return
     */
    List<Employee> selectEmployeeAllInfo();

    /**
     * 查询总记录数
     * @return
     */
    Integer selectEmployeeCountAll();

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    Employee selectEmployeeByNameAndPwd(@Param("loginName") String loginName, @Param("password") String password);
}