package kr.co.demo.son.demo.src.system.exception;

public class UsernameFromTokenException extends RuntimeException{
    public UsernameFromTokenException(String message){
        super(message);
    }
}