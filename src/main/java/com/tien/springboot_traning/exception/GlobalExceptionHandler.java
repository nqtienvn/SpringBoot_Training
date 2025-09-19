package com.tien.springboot_traning.exception;

import com.tien.springboot_traning.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //.status() là tự chỉ định status code
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }
//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse> handlingException(Exception exception) {
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setCode(ErrorCode.UNKNOW_ERROR.getCode());
//        apiResponse.setMessage(ErrorCode.UNKNOW_ERROR.getMessage());
//        return ResponseEntity.status(ErrorCode.UNKNOW_ERROR.getHttpStatusCode()).body(apiResponse);
//    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessException(AccessDeniedException accessException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.ACESS_DENIDED.getMessage());
        apiResponse.setCode(ErrorCode.ACESS_DENIDED.getCode());
        return ResponseEntity.status(ErrorCode.ACESS_DENIDED.getHttpStatusCode()).body(apiResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handlingMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
