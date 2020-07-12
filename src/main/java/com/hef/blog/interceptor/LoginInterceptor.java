package com.hef.blog.interceptor;

import com.hef.blog.util.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {


        /**
         * using Session
         */
        /*if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            return false;
        }*/

        /**
         * using jwt;
         * catch error: JWT String argument cannot be null or empty.
         */
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.sendRedirect("/admin");
            return false;
        }

        try {
            String username = JwtTokenUtils.getUsernameByToken(token);
            System.out.println("username: " + username);
            if (username == null){
                response.sendRedirect("/admin");
                return false;
            }

        } catch (Exception e ) {
            System.out.println(e.getMessage());
            response.sendRedirect("/admin");
            return false;
        }

        return true;
    }
}
