package kr.co.demo.son.demo.src.system.intercepter;


import kr.co.demo.son.demo.src.system.context.TestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
public class CommInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        log.info(" -- Header Information -- ");
        Enumeration<String> names = request.getHeaderNames();
        Collections.list(names).forEach(name -> log.info("    getHeader (" + name + ") : " + request.getHeader(name)));

        log.info(" -- Parameter Information -- ");
        Map<String, String[]> m = request.getParameterMap();
        Set<Map.Entry<String, String[]>> s = m.entrySet();
        Iterator<Map.Entry<String, String[]>> it = s.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> entry = it.next();
            String key = entry.getKey();
            String[] value = entry.getValue();
            log.info("    Key : {}" , key);
            if (value.length > 1) {
                log.info("     Value List");
                for (int i = 0; i < value.length; i++) {
                    log.info("       - {}" , value[i]);
                }
            } else
                log.info("    Value : {}" , value[0]);
        }

        log.info(" *********** [ Request Object Start] ********** ");
        log.info(" getContentLength : {}", request.getContentLength());
        log.info(" getCharacterEncoding : {}", request.getCharacterEncoding());
        log.info(" getContentType : {}", request.getContentType());
        log.info(" getProtocol : {}", request.getProtocol());
        log.info(" getMethod : {}", request.getMethod());
        log.info(" getRequestURI : {}", request.getRequestURI());
        log.info(" getContextPath : {}", request.getContextPath());
        log.info(" getServerName : {}", request.getServerName());
        log.info(" getServerPort : {}", request.getServerPort());
        log.info(" getRemoteAddr : {}", request.getRemoteAddr());
        log.info(" *********** [ Request Object End] ********** \n");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        TestContextHolder.clearContext();
    }
}
