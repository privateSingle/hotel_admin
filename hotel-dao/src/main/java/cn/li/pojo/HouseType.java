package cn.li.pojo;


import lombok.Data;

/**
 * @user: Mr.Wang
 * @date: 2020/1/7
 * @time: 17:05
 * @comment: null
 */
@Data
public class HouseType {
    /**
     * 客房类型ID
     */
    private Integer id;

    /**
     * 类型名
     */
    private String typeName;

    /**
     * 房间价格
     */
    private Integer price;

    /**
     *  房间图片
     */
    private String houseImg;

    /**
     *  房间总数
     */
    private Integer count;

    /**
     * 剩余空房
     */
    private Integer remain;

    /**
     *  描述
     */
    private String remark;

}