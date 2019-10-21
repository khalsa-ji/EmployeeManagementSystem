//  Waheguru Ji!

package com.khalsa_ji.ems.utils;

import java.util.regex.Pattern;

public class Validator {
    public enum code {ok, nullNumber, negativeNumber, zero, emptyString, nullString, invalidString};

    public static code validateID(Long ID) {
        if(ID == null)      return code.nullNumber;
        if(ID < 0)          return code.negativeNumber;
        if(ID == 0)         return code.zero;
        return code.ok;
    }

    public static code validateString(String str) {
        if(str == null)                 return code.nullString;
        if(str.compareTo("") == 0)      return code.emptyString;
        if(!Pattern.compile("^[ A-Za-z]+$").matcher(str).matches())
            return code.invalidString;
        return code.ok;
    }
}
