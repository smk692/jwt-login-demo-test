package kr.co.demo.son.demo.src.system.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;

@Getter
public class ErrorResponse {

    public int code;
    public String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String convertToJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}