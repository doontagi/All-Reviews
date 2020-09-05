package com.allreviews.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthCheckAspect {

    /**
     * 요청이 로그인 되어있는지 확인.
     *
     * @author hanul
     *
     */
    @Before("@annotation(com.allreviews.aop.LoginCheck)")
    public void loginCheck() {

        HttpSession session =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest()
                        .getSession();
        String userId = (String) session.getAttribute("LOGIN_USER_ID");

        if (userId == null) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "Login is required") { };
        }
    }
}
