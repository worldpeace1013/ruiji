package com.hyh.ruiji.filter;

import com.alibaba.fastjson.JSON;
import com.hyh.ruiji.common.BaseContext;
import com.hyh.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
@Slf4j
public class LoginCheckFilter implements Filter {

    //定义一个用于url匹配的PATH_MATCHER
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //定义放行的url
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };
        //获取当前请求的url
        String requestURI = httpServletRequest.getRequestURI();
        //判断此次请求是否放行
        boolean check = check(urls, requestURI);
        if (check){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //查看用户是否登入
        if (httpServletRequest.getSession().getAttribute("employee")!=null){
            Long employeeID = (Long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeID);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //如果未登入则跳转到登入页面
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public boolean check(String[] urls,String requestURI){
        for (String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }

}
