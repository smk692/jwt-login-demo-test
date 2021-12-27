package kr.co.demo.son.demo.src.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebRestController {
    @Operation(summary = "health check")
    @GetMapping("/")
    public String awsPingCheck() {
        return null;
    }

    @Operation(summary = "restCall test")
    @GetMapping("/webTest")
    public String webTest() {
        return "testApi call~!";
    }



}
