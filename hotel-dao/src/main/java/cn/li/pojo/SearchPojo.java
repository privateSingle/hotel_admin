package cn.li.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 23:01
 * @comment: 查询条件实体类
 */
@Data
public class SearchPojo implements Serializable {

    private String clientName;      //客户姓名

    private String clientPhone;     //客户手机号

    private String businessDate;    //营业日报表的日期

    private Integer sellTypeId;     //营业日报表的销售种类

}
