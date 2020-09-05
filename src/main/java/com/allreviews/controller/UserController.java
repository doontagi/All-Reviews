package com.allreviews.controller;

import com.allreviews.DTO.LoginRequestDTO;
import com.allreviews.User;
import com.allreviews.aop.LoginCheck;
import com.allreviews.service.LoginService;
import com.allreviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {

    private static final ResponseEntity<String> SUCCESSFUL_SIGNUP =
            new ResponseEntity<String>("Successful Sign-up", HttpStatus.OK);
    private static final ResponseEntity<String> BAD_SIGNUP =
            new ResponseEntity<String>("Wrong form", HttpStatus.BAD_REQUEST);

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    /**
     * 로그인 요청.
     *
     * @author hanul
     *
     * @param loginRequestDTO 로그인하고자 하는 유저의 username, password를 담은 DTO
     * @param session 사용자의 session
     * @return Http 상태 코드
     */
    @PostMapping("/login")
    public HttpStatus login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        User user = loginService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        if (user == null) {
            return HttpStatus.UNAUTHORIZED;
        } else {
            session.setAttribute("LOGIN_USER_ID", loginRequestDTO.getUsername());
            return HttpStatus.OK;
        }
    }


    /**
     * 회원가입 요청.
     *
     * @author hanul
     *
     * @param user 회원가입 하고자 하는 유저 정보를 담은 객체
     * @param errors 회원가입 요청이 정상적이지 않은 경우의 error 정보
     * @return Http 상태 코드를 포함한 ResponseEntity 객체
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return BAD_SIGNUP;
        }
        userService.createUser(user);

        return SUCCESSFUL_SIGNUP;
    }

    /**
     * 로그아웃 요청.
     *
     * @author hanul
     *
     * @param session 사용자의 세션
     * @return Http 상태 코드
     */
    @GetMapping("/logout")
    @LoginCheck
    public HttpStatus logout(HttpSession session) {
        session.removeAttribute("LOGIN_USER_ID");
        return HttpStatus.OK;
    }
}
