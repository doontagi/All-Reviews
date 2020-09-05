package com.allreviews.service;

import com.allreviews.User;

public interface LoginService {

    /**
     * 유저 로그인
     *
     * @author hanul
     *
     * @param username 로그인하고 싶은 user의 username
     * @param password 로그인하고 싶은 user의 password
     * @return 로그인에 성공한 user 객체
     */
    public User login(String username, String password);
}
