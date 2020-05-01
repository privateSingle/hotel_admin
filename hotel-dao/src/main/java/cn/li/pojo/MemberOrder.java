package cn.li.pojo;

import lombok.Data;

/**
 * @author: Mr.Wang
 * @date: 2020/02/15
 * @time: 12:32
 * @comment: 个人中心订单显示的实体类
 */
@Data
public class MemberOrder {

    private Integer id;             //订单ID

    private String orderCode;       //订单号

    private String orderType;       //订单类型（0房间预定 1餐品预定）

    private Integer money;          //订单价格

    private String createDate;      //订单创建时间

    private String orderStatus;     //订单状态 0已预订 1已完成 2已取消

    private Integer isComment;      //是否已评价 0未评论 1已评论

}
