package com.example.psycholisk.sawa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Psycholisk on 3/9/2016.
 */
public class utils {

    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
