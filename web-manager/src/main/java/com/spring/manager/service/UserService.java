package com.spring.manager.service;

import com.spring.manager.domain.User;
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // username has to be unique (exception)

        // make sure that password and confirm password match

        // we don't persist or show the confirm password
        return userRepository.save(user);
    }
}
