package com.app.product.controller;


import com.app.product.dto.SignUpDto;
import com.app.product.service.SignUpService;
import com.app.product.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    private Utility utility;
    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpDto signUpDto) {
        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();
        String city = signUpDto.getCity();

        if (!utility.validateInput(email) || !utility.validateInput(password) || !utility.validateInput(city))
            return "Please provide all mandatory data";

        return signUpService.signUp(signUpDto);
    }
}
