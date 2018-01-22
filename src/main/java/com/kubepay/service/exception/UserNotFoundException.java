package com.kubepay.service.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6387218283464744891L;
    public Long id;

    public UserNotFoundException(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
