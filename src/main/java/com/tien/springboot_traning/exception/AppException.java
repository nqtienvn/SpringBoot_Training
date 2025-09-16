package com.tien.springboot_traning.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException{
    ErrorCode errorcode;

    public AppException(ErrorCode errorcode) {
//        super(errorcode.getMessage());
        this.errorcode = errorcode;
    }

    public ErrorCode getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(ErrorCode errorcode) {
        this.errorcode = errorcode;
    }
}
