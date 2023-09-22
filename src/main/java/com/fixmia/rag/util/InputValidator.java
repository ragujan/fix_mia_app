package com.fixmia.rag.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9\\s]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile( "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final  Pattern PRICE_PATTERN = Pattern.compile( "^\\d{0,8}(\\.\\d{1,4})?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[A-Z])(?=.*[^a-zA-Z\\d]).{7,}$",Pattern.CASE_INSENSITIVE);
    public static boolean inputTextIsValid(String input) {
        return input != null && !input.isEmpty() && !containsSpecialCharacters(input);
    }
    public static boolean inputNumberIsValid(String number){
        return number !=null && !number.isEmpty() && isNumber(number);
    }
    public static boolean inputEmailIsValid(String email){
        return email != null && !email.isEmpty() && isEmail(email);
    }
    public static boolean inputPriceIsValid(String price){
        return price != null && !price.isEmpty() && isPrice(price);
    }
    private static boolean containsSpecialCharacters(String input) {
        Matcher matcher = SPECIAL_CHAR_PATTERN.matcher(input);
        return matcher.find();
    }
    private static boolean isNumber(String input){
        Matcher matcher = NUMBER_PATTERN.matcher(input);
        return matcher.matches();
    }
    private static boolean isEmail(String input){
        Matcher matcher = EMAIL_PATTERN.matcher(input);
        return matcher.matches();
    }
    private static boolean isPrice(String input){
        Matcher matcher = PRICE_PATTERN.matcher(input);
        return matcher.matches();
    }
    public static String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
    public static boolean validPasswod(String password){

       Matcher matcher = PASSWORD_PATTERN.matcher(password);
       return matcher.matches();
    }
    public static boolean areCharArraysEqual(char[] array1, char[] array2) {
        if (array1.length != array2.length) {
            return false; // If the arrays have different lengths, they can't be equal
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false; // If any character differs, the arrays are not equal
            }
        }

        return true; // All characters matched, so the arrays are equal
    }

}
