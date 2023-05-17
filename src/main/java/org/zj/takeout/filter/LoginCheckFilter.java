package org.zj.takeout.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.zj.takeout.common.BaseContext;
import org.zj.takeout.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")//全部拦截
public class LoginCheckFilter implements Filter {
//    路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
//      不需要做判断的访问路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
//      判断访问路径是否在Urls中，如果在直接放行
        if (pathMatch(urls,requestURI)){
            filterChain.doFilter(request,response);
            return;
        }

//     如果不在urls中判断是否登录，如果已经登录就放行
        HttpSession session = request.getSession();
        if (session.getAttribute("employee")!=null){

            Long employeeId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setThreadLocal(employeeId);
            filterChain.doFilter(request,response);

            return;
        }
//      如果未登录，则返回前端数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));


    }

    public boolean pathMatch(String[] urls, String requestURI){
        for (String url : urls) {
            if (PATH_MATCHER.match(url,requestURI)){
                return true;
            }
        }
        return false;
    }

}
