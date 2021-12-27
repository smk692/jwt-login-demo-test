package kr.co.demo.son.demo.src.system.exception.handler;


import kr.co.demo.son.demo.src.system.exception.BadRequestException;
import kr.co.demo.son.demo.src.system.exception.ResourceNotFoundException;
import kr.co.demo.son.demo.src.system.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * NumberFormatException
     * */
    @ExceptionHandler(NumberFormatException.class)
    protected ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException e) {
        log.error("NumberFormatException", e);
        String message = Optional.ofNullable(e.getMessage()).orElseGet(() -> "NumberFormatException");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.SC_BAD_REQUEST, message);
        return ResponseEntity.ok().body(errorResponse);
    }
    /**
     * 사용자 정의 예외처리
     * */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest req, BadRequestException e) {
        String message = Optional.ofNullable(e.getMessage()).orElseGet(() -> "올바른 요청이 아닙니다. ");
        String errorCode = Optional.ofNullable(e.getErrorCode()).orElseGet(() -> "400");

        ErrorResponse errorResponse = new ErrorResponse(Integer.parseInt(errorCode), message);
        return ResponseEntity.ok().body(errorResponse);
    }
    /**
     * Exception 나머지 에러 일 경우
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, Exception e) {
        log.error("Exception", e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.ok().body(errorResponse);
    }

}