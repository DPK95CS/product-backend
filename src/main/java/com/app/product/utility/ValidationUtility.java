package com.app.product.utility;

import org.springframework.stereotype.Service;

@Service
public class ValidationUtility {
    public boolean validateInput(String input) {
        if (input == null || input.equals(""))
            return false;
        return true;
    }
}
