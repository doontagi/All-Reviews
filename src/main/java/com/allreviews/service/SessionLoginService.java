package com.allreviews.service;

import com.allreviews.User;
import com.allreviews.repository.UserRepository;
import com.allreviews.utils.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionLoginService implements LoginService{

    @Autowired
    UserRepository userRepo;

    @Override
    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("No user has that id");
        }
        String encryptedPassword = SHA256Util.encrypt(password);
        if (!user.getPassword().equals(encryptedPassword)) {
            throw new RuntimeException("Incorrect Password");
        }
        return user;
    }
}
