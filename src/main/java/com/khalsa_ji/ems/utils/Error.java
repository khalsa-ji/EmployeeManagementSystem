//  Waheguru Ji!

package com.khalsa_ji.ems.utils;

/**
 * The class {@code Error} defines a minimal set of properties required for keeping track of
 * any validation errors such as error codes or error messages(description) and provides some
 * methods to play around with them
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Validator.code
 */

public class Error {
    private Validator.code errorCode;
    private String errorMessage;

    //  Default constructor is made private, intentionally.
    //  So as to allow creation of objects through the parameterised constructor only.
    //  It assures that npo instance of this class could be instantiated with invalid default field values.
    private Error() {}

    /**
     * Constructor for creating an instance of the {@code Error} class.
     *
     * @param errorCode An instance of the {@code Validator.code}, describing an error code
     * @param errorMessage A string describing the error message
     * @see Validator.code
     */

    public Error(Validator.code errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Method to fetch an instance of {@code Validator.code}, which defines a validation error code
     *
     * @return An instance of the {@code Validation.code}
     * @see Validator.code
     */

    public Validator.code getErrorCode() {
        return errorCode;
    }

    /**
     * Method to fetch a description of the error code i.e. error message
     *
     * @return A string describing error code - error message
     */

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Method to represent {@code Error} class object in {@code java.lang.String} format
     * @return String({@code java.lang.String}) representation of {@code Error} class object
     */

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
