package cn.li.service;

import cn.li.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.li.mapper.CommentMapper;
import cn.li.pojo.Comment;

import java.util.List;

/**
  * @user: Mr.Wang
  * @date: 2020/1/7
  * @time: 17:05
  * @comment: null
  */
@Service
public class CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private OrderService orderService;

    /**
     * 根据ID删除一条评价信息
     * @param id
     * @return
     */
    public Integer deleteByPrimaryKey(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    /**
     * 选择性插入一条评价
     * @param record 要插入的评价对象
     * @param order 要修改评价状态的订单
     * @return
     */
    public int insertSelective(Comment record, Order order) {
        //插入评价
        int result = commentMapper.insertSelective(record);
        //判断是否插入成功
        if(result > 0){
            //插入成功，继续更新订单的评价状态
            Integer updateResult = orderService.updateByPrimaryKeySelective(order);
            //判断是否更新成功
            if(updateResult > 0){
                return 1;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }

    
    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

    /**
     * 分页查询全部
     * @param page  //当前页
     * @param limit //每页记录数
     * @param clientId //用户ID
     * @return
     */
    public List<Comment> selectAllByPage(Integer page, Integer limit, Integer clientId){
        //计算分页开始条件（当前页-1*每页显示的行数）
        Integer start = (page-1)*limit;
        return commentMapper.selectAllByPage(start, limit, clientId);
    }

    /**
     * 查询全部总记录数，根据用户ID查询
     * @param clientId
     * @return
     */
    public Integer selectAllCountByClientId(Integer clientId) {
        return commentMapper.selectAllCountByClientId(clientId);
    }

    public List<Comment> getComments(Integer houseTypeId) {
        return commentMapper.selectComments(houseTypeId);
    }
}
