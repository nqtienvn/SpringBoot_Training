package com.tien.springboot_traning.exception;

public enum ErrorCode {
    USER_NOT_EXTSTED(0000, "USER NOT FOUND"),
    PASS_INCORECT(5555, "PASS WORD INCORECT"),
    ERROR_TOKEN(9999, "lỗi token")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
