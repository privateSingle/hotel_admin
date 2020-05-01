package cn.li.pojo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    /**
    * 自增列
    */
    private Integer id;

    /**
    * 入住人ID(客人ID)
    */
    private Integer clientId;

    /**
    * 员工ID
    */
    private Integer empId;

    /**
    * 订单类型(0:订房;1:订餐)
    */
    private Integer orderType;

    /**
    * 入住时间
    */
    private String createDate;

    /**
    * 订单价格
    */
    private Integer money;

    /**
    * 订单编号(时间(精确到秒)+入住人ID+房间号)
    */
    private String orderCode;

    /**
     * 预定房间数量
     */
    private Integer reserverCount;

    private Integer houseTypeId;

    /**
     * 员工的对象
     */
    private Employee employee;

    /**
     * 客户的对象
     */
    private Client client;

    /**
     * 订单状态 0已预订 1已完成 2已取消 3已入住
     */
    private Integer orderStatus;

    /**
     * 是否评价 订单是否已评论过，0未评论 1已评论 2该订单不支持评论
     */
    private Integer isComment;

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}