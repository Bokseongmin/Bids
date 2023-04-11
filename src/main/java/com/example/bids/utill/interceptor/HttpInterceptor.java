package com.example.bids.utill.interceptor;

import com.example.bids.entity.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        log.info(">>> 인터셉터 요청 전");
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        if(userDto == null) {
            res.sendRedirect("/sign/in");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">>> 인터셉터 요청 후");
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
        log.info(">>> 인터셉터 처리 후");
    }
}
