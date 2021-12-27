package kr.co.demo.son.demo.src.system.config;


import kr.co.demo.son.demo.src.system.filter.ExceptionHandlerFilter;
import kr.co.demo.son.demo.src.system.filter.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final JwtTokenFilter jwtTokenFilter;
    final ExceptionHandlerFilter exceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/member")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/")
                .permitAll()
                .antMatchers("/v2/api-docs","/api-docs/**", "/swagger-resources/**", "/**/swagger-ui/**", "/webjars/**","/swagger-ui.html","/webTest")
                .permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()
                .and()
                .antMatcher("/**")
                .addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtTokenFilter.class);
    }
}