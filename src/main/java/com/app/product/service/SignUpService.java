package com.app.product.service;

import com.app.product.dto.SignUpDto;
import com.app.product.model.Users;
import com.app.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    public String signUp(SignUpDto signUpDto) {
        List<Users> usersList = userRepository.findByEmail(signUpDto.getEmail());
        if (usersList == null || usersList.isEmpty()) // new user
        {
            Users users = new Users();
            users.setEmail(signUpDto.getEmail());
            users.setCity(signUpDto.getCity());
            users.setPassword(signUpDto.getPassword());
            userRepository.save(users);
            return "User signed up successfully for " + signUpDto.getEmail() + " Please login..";
        } else {
            return "User already exists for email: " + signUpDto.getEmail() + " Please login..";
        }
    }
}
