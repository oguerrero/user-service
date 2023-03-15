package com.oguerrero.userservice.Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

public class Utils {

    public static String format(String str) {
        str = str.replaceAll("[^a-zA-Z]", "");
        str = str.trim();
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    public static boolean formatEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null)
            return false;

        return pattern.matcher(email).matches();
    }

    public static boolean formatPhoneNumber(String phoneNumber) {
        HashSet<Character> phoneSet = new LinkedHashSet<>(phoneNumber.length() - 1);
        for (char c : phoneNumber.toCharArray()) {
            phoneSet.add(c);
        }

        if (phoneNumber.length() < 6 || phoneSet.size() < 4) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[0-9]{6,10}$");

        return pattern.matcher(phoneNumber).matches();
    }

}
