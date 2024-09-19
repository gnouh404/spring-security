package com.huongnguyen.exception;

import com.huongnguyen.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException e){
        return ResponseEntity
                .status(e.getErrorCode().getCode())
                .body(new ApiResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }

//    @ExceptionHandler(value = AccessDeniedException.class)
//    public ResponseEntity<ApiResponse> handleAccessDeniedException(){
//
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body(new ApiResponse(403, ErrorCode.ACCESS_DENIED.getMessage()));
//    }
}
