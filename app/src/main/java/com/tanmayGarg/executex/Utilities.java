package com.tanmayGarg.executex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Class made to define methods that are used often by various classes adhering to abstraction (hiding implementation detail)
public class Utilities {

    //Regex: Short for regular expression, a regex is a string of text that allows you to create patterns that help match, locate, and manage text
    public static boolean checkValidityEmail(String emailId) {
        //Regular expression (regex) {specifies a search pattern in text} to check validity for email [works most of the time]
        //https://stackoverflow.com/questions/8204680/java-regex-email
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }

    public static boolean checkValidityPassword(String password) {
        //Regular expression (regex) {specifies a search pattern in text} to check validity for password [works most of the time]
        //https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
