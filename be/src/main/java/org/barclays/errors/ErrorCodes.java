package org.barclays.errors;

public enum ErrorCodes{
    UNKNOWN_ERROR("UE-100","Unknown error occured"),
    UNKNOWN_ACCESS("UE-101","Unknown Access"),

    SIGNUP_TYPE_ERROR("SU-100","Invalid type, must be [store,customer]"),
    SIGNUP_MISSING_DETAILS("SU-101","Details Missing. Please complete."),

    LOGIN_MISSING_DETAILS("LO-100","Incomplete credentials"),
    LOGIN_NOT_FOUND("LO-101","No such user found"),
    LOGIN_INVALID("LO-102","Invalid credentials. Please check and try again"),
    LOGIN_ALREADY_AVAILABLE("LO-104","Session already available,please logout first."),

    CART_MISSING_DETAILS("CA-100","Incomplete details"),
    CART_INVALID_PRODUCT("CA-101","Invalid product supplied"),
    CART_EMPTY("CA-102","Cart is Empty"),
    CART_UNAVAILABLE_QUANTITY("CA-103","Item quantity less in inventory");

    private final String errorCode;

    private final String errorMessage;

    ErrorCodes(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorCodes{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
