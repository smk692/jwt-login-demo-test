package kr.co.demo.son.demo.src.system.filter;

import kr.co.demo.son.demo.src.system.exception.UsernameFromTokenException;
import kr.co.demo.son.demo.src.system.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (UsernameFromTokenException ex){
            log.info(ex.getMessage());
            log.info("UsernameFromTokenException handler filter");
            loggingRequest(request);
            setErrorResponse(HttpStatus.FORBIDDEN,response,ex);
        } catch (RuntimeException ex){
            log.info("RuntimeException exception handler filter");
            loggingRequest(request);
            setErrorResponse(HttpStatus.FORBIDDEN,response,ex);
        } catch (Exception ex){
            log.info("Exception exception handler filter");
            loggingRequest(request);
            setErrorResponse(HttpStatus.FORBIDDEN,response,ex);
        }

    }

    public void loggingRequest(HttpServletRequest request) {
        log.info(" *********** [ Filter Exception Request Object Start] ********** ");
        log.info(" getContentLength : {}" , request.getContentLength());
        log.info(" getCharacterEncoding : {}" , request.getCharacterEncoding());
        log.info(" getContentType : {}" , request.getContentType());
        log.info(" getProtocol : {}" , request.getProtocol());
        log.info(" getMethod : {}" , request.getMethod());
        log.info(" getRequestURI : {}" , request.getRequestURI());
        log.info(" getContextPath : {}" , request.getContextPath());
        log.info(" getServerName : {}" , request.getServerName());
        log.info(" getServerPort : {}" , request.getServerPort());
        log.info(" getRemoteAddr : {}" , request.getRemoteAddr());
        log.info(" *********** [ Filter Exception Request Object End] ********** \n");
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response,Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());

        try{
            String json = errorResponse.convertToJson();
            response.setContentLength(json.length());
            response.getWriter().print(json);
            response.getWriter().flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
        excludeUrlPatterns.add("/swagger-ui.html");
        excludeUrlPatterns.add("/webjars/**");
        excludeUrlPatterns.add("/**/swagger-ui/**");
        excludeUrlPatterns.add("/swagger-resources/**");
        excludeUrlPatterns.add("/api-docs/**");
        excludeUrlPatterns.add("/v2/api-docs");
        return excludeUrlPatterns.stream().anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}