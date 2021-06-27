package com.app.product.controller;


import com.app.product.dto.SignUpDto;
import com.app.product.model.Users;
import com.app.product.repository.UserRepository;
import com.app.product.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SignUpController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationUtility validationUtility;

    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpDto signUpDto) {
        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();
        String city = signUpDto.getCity();

        if (!validationUtility.validateInput(email) || !validationUtility.validateInput(password) || !validationUtility.validateInput(city))
            return "Please provide all mandatory data";

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
