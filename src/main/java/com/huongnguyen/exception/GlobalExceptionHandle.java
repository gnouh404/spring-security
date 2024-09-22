package com.huongnguyen.exception;

import com.huongnguyen.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex){
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getField()).append(" ").append(error.getDefaultMessage()).append("; ");
        }

        // Ném ra AppException với mã lỗi VALIDATION_ERROR
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(400, ErrorCode.INVALID_CREDENTIALS.getMessage()));
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ApiResponse> handleTokenRefreshException(TokenRefreshException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }
}
