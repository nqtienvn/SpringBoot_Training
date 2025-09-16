package com.tien.springboot_traning.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode{
    UNKNOW_EXCEPTION(9999, "unknow exception"),
    USER_EXISTED(1000, "user existed"),
    USER_EMPTY(0000, "users empty"),
    USER_NOT_FOUND(1111, "user not found"),
    USERNAME_AT_LEST_3(2222, "user name at least 3 character"),
    AGE_ERROR(3333,"Age can not < 1")
    ;//phân cách constant và cách field
    //class chỉ định từng code của từng lỗi
    //dùng design lỗi cho dự án
    int code;
    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
