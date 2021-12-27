package kr.co.demo.son.demo.src.utils;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
public class SmsMessageUtil {
    @Value("${coolsms.devHee.apikey}")
    private String apiKey;

    @Value("${coolsms.devHee.apisecret}")
    private String apiSecret;

    @Value("${coolsms.devHee.fromnumber}")
    private String fromNumber;

    public JSONObject sendMessage(String toNumber, String randomNumber) throws CoolsmsException {

        Message coolSms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", toNumber);
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("text", "[son test] 인증번호 "+randomNumber+" 를 입력하세요.");
        params.put("app_version", "test application 1.0");

        JSONObject obj = coolSms.send(params);
        log.info("{}", obj.toString());

        return obj;
    }
}
