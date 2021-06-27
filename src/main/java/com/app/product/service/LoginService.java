package com.app.product.service;

import com.app.product.model.Users;
import com.app.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String login(String email, String password) {
        List<Users> usersList = userRepository.findByEmail(email);
        if (usersList == null || usersList.isEmpty()) // new user
        {
            return "User does not exists for " + email + " Please signup..";
        } else {
            if (password.equals(usersList.get(0).getPassword())) {
                String uuid = UUID.randomUUID().toString();
                redisTemplate.opsForSet().add(uuid, usersList.get(0).getId());
                return "User logged in successfully.";
            } else {
                return "Password is wrong. Please enter correct password ";
            }
        }
    }

    public String logout(String email) {
        List<Users> users = userRepository.findByEmail(email);
        if(users != null && !users.isEmpty())
        {
            Set<String> keys = redisTemplate.keys("*");
            if(keys.isEmpty())
                return "Logged out.Please log in again: " + email;
            for(String key : keys)
                redisTemplate.opsForSet().remove(key,users.get(0).getId());
            return "Logged out successfully for email: " + email;
        }
        return "User does not exist for email: " + email;

    }
}
