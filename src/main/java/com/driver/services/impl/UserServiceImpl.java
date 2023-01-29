package com.driver.services.impl;

import com.driver.model.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {
        userRepository4.deleteById(userId);

    }

    @Override
    public User updatePassword(Integer userId, String password) throws Exception {
        User user = userRepository4.getOne(userId);
        if(user==null)
            throw new Exception();
        user.setPassword(password);
        userRepository4.save(user);
        return user;

    }

    @Override
    public void register(String name, String phoneNumber, String password) {
        userRepository4.save(new User(name,phoneNumber,password));

    }
}
