package com.kubepay.service.exception;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -5966528411310530440L;

    private boolean validation;

    public UserServiceException(String msg, boolean validation){
        super(msg);
        this.validation=validation;
    }

    public boolean isValidation() {
        return validation;
    }

}
