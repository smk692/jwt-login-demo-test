package kr.co.demo.son.demo.src.system.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

    public String message;
    public int statusCode;
    public T response;

    public CommonResponse(T response) {
        this.message = "success";
        this.statusCode = 200;
        this.response = response;
    }
}