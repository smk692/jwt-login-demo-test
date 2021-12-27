package kr.co.demo.son.demo.src.system.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",securityScheme()))
                .security(Arrays.asList(securityRequirement()))
                .info(setInfo());
    }

    public Info setInfo() {
        return new Info().title("login demo API")
                .version("X-API-VERSION=1")
                .description("login demo Swagger 입니다.");
    }

    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
    }
    public SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList("bearerAuth");
    }
}
