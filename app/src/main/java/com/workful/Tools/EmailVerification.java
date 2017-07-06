package com.workful.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cristian on 5/5/2017.
 */
public class EmailVerification {

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean login_email(String email){
        return email.contains("@");
    }
}
