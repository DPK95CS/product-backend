package com.app.product.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Set<String> objectId = redisTemplate.opsForSet().members(request.getHeader("token"));

        //if (objectId.equals("null") || objectId.equals(""))
        if(objectId.isEmpty())
        {
            response.sendError(401,"Not Authorised");
            return false;
        }
        return true;
    }
}
