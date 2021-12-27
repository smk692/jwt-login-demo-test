package kr.co.demo.son.demo.src.system.response;

import lombok.Getter;

@Getter
public class DataResponse<T> {

    public int statusCode;
    public T data;

    public DataResponse(T data) {
        this.statusCode = 200;
        this.data = data;
    }
}