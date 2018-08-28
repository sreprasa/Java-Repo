package com.intv.checklist.security;

import com.intv.checklist.cache.ApplicationCache;
import com.intv.checklist.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Component
public class AuthorizationInterceptor  extends HandlerInterceptorAdapter {

    @Autowired
    private ApplicationCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //Read the userId from request and validate against data base user list.
        //For now creating security with all users, Ideally this context object represents the logged in user object.
        request.setAttribute(Constants.CHECKLIST_AUTHORIZATION_CONTEXT,new CheckListSecurityContext(cache.getUsers().stream().map(u -> u.getUserId()).collect(Collectors.toList())));
        return  true;
    }
}
