package com.tien.springboot_traning.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    USER_NOT_EXTSTED(0000, "USER NOT FOUND", HttpStatus.NOT_FOUND),
    PASS_INCORECT(5555, "PASS WORD INCORECT", HttpStatus.BAD_REQUEST),
    ERROR_TOKEN(9999, "lỗi token", HttpStatus.UNAUTHORIZED),
    UNKNOW_ERROR(1234, "lỗi lạ", HttpStatus.INTERNAL_SERVER_ERROR), //500
    NAME_EXIST(113, "tên đã tồn tại", HttpStatus.BAD_REQUEST),
    ACESS_DENIDED(115, "không có quyền truy cập", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(401, "không xác minh được", HttpStatus.UNAUTHORIZED),
    EMPTY(9, "trong database không có dữ liệu", HttpStatus.NOT_FOUND)
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}