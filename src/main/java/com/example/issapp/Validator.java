package com.example.issapp;
import java.util.regex.*;

public class Validator {
    public static boolean verificaParola(String parola){
        if (parola.length() < 10) {
            return false;
        }

        // Verifica dacă conține cel puțin o literă mare și o cifră
        Pattern pattern = Pattern.compile("(?=.*[A-Z])(?=.*\\d)");
        Matcher matcher = pattern.matcher(parola);
        return matcher.find();
    }

    public static boolean verificaCNP(String cnp) {
        if (cnp.length() != 13) {
            return false;
        }
        if (cnp.charAt(0) != '5' && cnp.charAt(0) != '6') {
            return false;
        }
        for (int i = 0; i < cnp.length(); i++) {
            if (!Character.isDigit(cnp.charAt(i))) {
                return false;
            }
        }

        return true;
    }


}
