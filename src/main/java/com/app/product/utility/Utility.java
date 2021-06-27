package com.app.product.utility;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Utility {

    public boolean validateInput(String input) {
        if (input == null || input.equals(""))
            return false;
        return true;
    }

    public void makeTagsLowerCase(List<String> tags) {
        for (int i = 0; i < tags.size(); i++)
            tags.set(i, tags.get(i).toLowerCase());
    }
}
