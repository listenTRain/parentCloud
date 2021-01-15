package com.java.admin.interceptor;

import com.java.admin.model.AdminUsersEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *   自定义用户登录的拦截器
 *     作用之一：防盗链
 */
public class MyInterceptor implements HandlerInterceptor {

    //拦截的核心方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("执行了preHandle方法。。。。");
        //1.得到session容器
        HttpSession session = request.getSession();
        //2.取到session容器中的登录的用户
        AdminUsersEntity user = (AdminUsersEntity) session.getAttribute("loginUser");
        //3.根据用户是否存在判断其有没有登录
        if(user!=null){
            return true;  //此拦截器放行该请求，继续执行其它的拦截器
        }else {
            //4.执行其他操作
            System.out.println("你还没有登陆，请先登陆。。");
            //在request域中装入拦截提示
            request.setAttribute("loginMsg","loginMsg");
            //转发到登陆页面
            request.getRequestDispatcher("/").forward(request,response);
            return false;  //表示该请求被拦截下来了
        }

    }

    //拦截的执行POST请求的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("执行了postHandle方法。。。。");
    }

    //拦截的执行之后的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        System.out.println("执行了afterCompletion方法。。。。");

    }
}
