package cn.li.pojo;

import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Employee {
    private Integer id;

    /**
    *  登录名
    */
    private String loginName;

    /**
    *  登陆密码
    */
    private String password;

    /**
    * 收银员名字
    */
    private String name;

    /**
    * 权限ID
    */
    private Integer powerId;
}