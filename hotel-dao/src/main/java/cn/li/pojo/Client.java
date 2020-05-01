package cn.li.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Client {
    /**
    * 客户ID
    */
    private Integer id;

    /**
    * 客户姓名
    */
    private String name;

    /**
    * 年龄
    */
    private Integer age;

    /**
    * 性别
    */
    private String sex;

    /**
    * 身份证号
    */
    private String idCard;

    /**
    *  手机号
    */
    private String phone;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 密码
    */
    private String password;

    /**
    * 是否是VIP(0:不是;1:是)
    */
    private Integer isVip;

    /**
    * 积分(只有会员才能充值积分)
    */
    private Integer score;

     /**
      * 账户余额（只有会员有）
      */
    private Integer balance;

    /**
    * 描述
    */
    private String remark;

    /**
     * 创建时间
     */
    private Long creatTime;
}
