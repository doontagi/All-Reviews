package com.allreviews.service;

import com.allreviews.User;
import com.allreviews.repository.UserRepository;
import com.allreviews.utils.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    /**
     * 유저 생성.
     *
     * @author hanul
     *
     * @param user 생성을 원하는 유저 객체
     */
    public void createUser(User user) {
        user.setPassword(SHA256Util.encrypt(user.getPassword()));
        if (userRepo.findByUsername(user.getUsername()) == null) {
            userRepo.save(user);
        } else {
            throw new RuntimeException("Duplicate Username");
        }
    }
}
