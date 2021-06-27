package com.app.product.controller;

import com.app.product.dto.LoginDto;
import com.app.product.service.LoginService;
import com.app.product.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private Utility utility;
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        if (!utility.validateInput(email) || !utility.validateInput(password))
            return "Please provide all mandatory data";
        return loginService.login(email, password);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        if (!utility.validateInput(email))
            return "Please provide all mandatory data";
        return loginService.logout(email);
    }
}
