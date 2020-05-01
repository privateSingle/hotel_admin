package cn.li.interceptor;

import cn.li.pojo.Employee;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Mr.Wang
 * @date: 2020/01/21
 * @time: 11:42
 * @comment: 拦截
 */
public class AdminInterceptor implements HandlerInterceptor {

    /**
     * 跳转前拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //每次跳转前判断当前是否已经是登录状态，如果不是则跳到登录页面，防止直接输入网址越过登录访问
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        if(employee == null) {
            //判断是否是Ajax请求  获取到请求头中的Ajax参数
            String XRequested =request.getHeader("X-Requested-With");
            //Ajax请求中带有的参数
            String ajaxReq = "XMLHttpRequest";
            if(ajaxReq.equals(XRequested)){
                Map<String, Object> map = new HashMap<>(1);
                map.put("result","IsAdminAjax");
                String data = JSON.toJSONString(map);
                response.getWriter().write(data);
                return false;
            }else{
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }
        }
        return true;
    }
}
