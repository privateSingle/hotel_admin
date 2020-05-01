package cn.li.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yajun
 * @create 2020/1/7
 * 评价表控制层
 */
@Controller
@RequestMapping("/main")
public class CommentController {

    @GetMapping("/openComment")
    public String openComment(){
        return "comment/commentList";
    }
}
