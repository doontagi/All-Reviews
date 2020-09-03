package com.allreviews.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthCheckAspect {

    @Before("@annotation(com.allreviews.aop.LoginCheck)")
    public void LoginCheck() {

        HttpSession session =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest()
                        .getSession();
        String userId = (String) session.getAttribute("LOGIN_USER_ID");

        if(userId == null) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "Login is required") {};
        }
    }
}
