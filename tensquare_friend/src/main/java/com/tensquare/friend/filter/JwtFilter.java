package com.tensquare.friend.filter;

import com.tensquare.tools.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("======================> 经过拦截器");
        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Token ")){
            String token = authorization.substring(6);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims != null){
                if("normal".equals(claims.get("roles"))){
                    request.setAttribute("normal_claims", claims);
                }
                if("admin".equals(claims.get("roles"))){
                    request.setAttribute("admin_claims", claims);
                }
            }
        }
        return true;
    }
}
