package com.tanmayGarg.executex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static boolean checkValidityEmail(String emailId) {
        //regular expression (regex) {specifies a search pattern in text} to check validity for email [works most of the time]
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }

    public static boolean checkValidityPassword(String password) {
        //regular expression (regex) {specifies a search pattern in text} to check validity for password [works most of the time]
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
