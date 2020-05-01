package cn.li.pojo;

import lombok.Data;

 /**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Data
public class Multiple {
    /**
    * 连锁店ID
    */
    private Integer id;

    /**
    * 店名
    */
    private String name;

    /**
    * 地址
    */
    private String address;

    /**
    * 营业状态
    */
    private String business;

    /**
    * 店主名
    */
    private String masterName;

    /**
    * 联系电话
    */
    private String phone;
}