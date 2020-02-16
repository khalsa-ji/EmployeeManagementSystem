//  Waheguru Ji!

package com.khalsa_ji.ems.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * The class {@code Validator} consists of some pre-defined error codes and
 * some method implementations that validate given data and produces appropriate error codes
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see SpringContext
 */

public class Validator {
    private static MessageSource messageSource;
    public enum code {ok, nullNumber, negativeNumber, zero, emptyString, nullString, invalidString}

    /**
     * Method to initialise an instance of the {@code MessageSource} interface's implementation,
     * a workaround for @Autowired not working in Non-Spring managed class
     *
     * @see SpringContext
     */

    private static void initialise() {
        ApplicationContext context = SpringContext.getAppContext();
        messageSource = (MessageSource) context.getBean("messageSource");
    }

    /**
     * Method to validate ID
     *
     * @param ID An instance of {@code Long} class
     * @param context An array of {@code Object} class
     * @return An instance of the {@code Error} class
     * @see Error
     */

    public static Error validateID(Long ID, Object[] context) {
        if(messageSource == null)
            initialise();

        if(ID == 0)
            return new Error(code.zero, messageSource.getMessage("error.code.zero", context, Locale.getDefault()));

        if(ID == null)
            return new Error(code.nullNumber, messageSource.getMessage("error.code.nullNumber", context, Locale.getDefault()));

        if(ID < 0)
            return new Error(code.negativeNumber, messageSource.getMessage("error.code.negativeNumber", context, Locale.getDefault()));

        return new Error(code.ok, messageSource.getMessage("error.code.ok", null, Locale.getDefault()));
    }

    /**
     * Method to validate a string
     *
     * @param str An instance of {@code String} class
     * @param context An array of {@code Object} class
     * @return An instance of the {@code Error} class
     * @see Error
     */

    public static Error validateString(String str, Object[] context) {
        if(messageSource == null)
            initialise();

        if(str == null)
            return new Error(code.nullString, messageSource.getMessage("error.code.nullString", context, Locale.getDefault()));

        if(str.compareTo("") == 0)
            return new Error(code.emptyString, messageSource.getMessage("error.code.emptyString", context, Locale.getDefault()));

        if(!Pattern.compile("^[ A-Za-z]+$").matcher(str).matches())
            return new Error(code.invalidString, messageSource.getMessage("error.code.invalidString", context, Locale.getDefault()));

        return new Error(code.ok, messageSource.getMessage("error.code.ok", null, Locale.getDefault()));
    }
}
