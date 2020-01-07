package com.spring.manager.service;

import com.spring.manager.domain.User;
import com.spring.manager.exception.EmailHasDuplicateException;
import com.spring.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {
        // username has to be unique (exception)
        try {
            String bCryptPassword = bCryptPasswordEncoder.encode(user.getPassword());

            user.setPassword(bCryptPassword);
            user.setConfirmPassword(bCryptPassword);

            return userRepository.save(user);
        } catch (Exception e) {
            throw new EmailHasDuplicateException("Email '".concat(user.getUsername()).concat("' already exists"));
        }
    }
}
