package kr.co.demo.son.demo.src.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends  RuntimeException {

    private String errorCode;
    private String message;

    public BadRequestException(final Throwable exception) {super(exception); }

    public BadRequestException(final String message, final Throwable exception) {
        super(message, exception);
        this.message = message;
    }

    public BadRequestException(final String message, String errorCode, final Throwable exception) {
        super(message, exception);
        this.message = message;
        this.errorCode = errorCode;
    }

    public BadRequestException(final String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(final String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public BadRequestException(final String message, int errorCode) {
        super(message);
        this.message = message;
        this.errorCode = Integer.toString(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseStatusException raise(String message){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}