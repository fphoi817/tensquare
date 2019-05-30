package com.tensquare.manage.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tensquare.tools.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManageFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("manage zuul 过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        String url = request.getRequestURI().toString();
        if(url.indexOf("/admin/login") > 0){
            System.out.println("登录页面" + url);
            return null;
        }
        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Token ")){
            String token = authorization.substring(6);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims != null){
                if("admin".equals(claims.get("roles"))){
                    requestContext.addZuulRequestHeader("Authorization", authorization);
                    System.out.println("token 通过验证. 添加头信息"+ authorization);
                    return null;
                }
            }
        }
        requestContext.setSendZuulResponse(false);      // 令zuul过滤该请求，不对其进行路由
        requestContext.setResponseStatusCode(501);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
