package com.lhc.sms.service;

import com.lhc.sms.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User findByPhone(String username){
        return new User(username,"123456");
    }
}
