package cn.li.interceptor;

import cn.li.pojo.Client;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Mr.Wang
 * @date: 2020/02/14
 * @time: 16:24
 * @comment: null
 */
public class WebInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //每次跳转前判断当前是否已经是登录状态，如果不是则跳到登录页面，防止直接输入网址越过登录访问
        Client client = (Client) request.getSession().getAttribute("clientUser");
        if(client == null) {
            response.sendRedirect(request.getContextPath()+"/webLogin");
            return false;
        }
        return true;
    }
}
