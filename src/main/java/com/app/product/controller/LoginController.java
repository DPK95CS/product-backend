package com.app.product.controller;

import com.app.product.dto.LoginDto;
import com.app.product.model.Users;
import com.app.product.repository.UserRepository;
import com.app.product.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationUtility validationUtility;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        if (!validationUtility.validateInput(email) || !validationUtility.validateInput(password))
            return "Please provide all mandatory data";

        List<Users> usersList = userRepository.findByEmail(email);
        if (usersList == null || usersList.isEmpty()) // new user
        {
            return "User does not exists for " + email + " Please signup..";
        } else {
            if (password.equals(usersList.get(0).getPassword())) {
                return "User logged in successfully.";
            } else {
                return "Password is wrong. Please enter correct password ";
            }
        }
    }


    //logout
}
