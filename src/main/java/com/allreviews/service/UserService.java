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

    public User createUser(User user) {
        user.setPassword(SHA256Util.encrypt(user.getPassword()));
        if (userRepo.findByUsername(user.getUsername()) == null) {
            userRepo.save(user);
        } else {
            throw new RuntimeException("Duplicate Username");
        }
        return user;
    }

    public User deleteUser(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("No user has that id");
        }
        if (user.getPassword() != SHA256Util.encrypt(password)) {
            throw new RuntimeException("Incorrect Password");
        }
        return user;
    }
}