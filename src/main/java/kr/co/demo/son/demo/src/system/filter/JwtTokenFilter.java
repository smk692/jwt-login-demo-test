package kr.co.demo.son.demo.src.system.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import kr.co.demo.son.demo.src.system.context.TestContextHolder;
import kr.co.demo.son.demo.src.system.exception.UsernameFromTokenException;
import kr.co.demo.son.demo.src.system.filter.token.JwtTokenManager;
import kr.co.demo.son.demo.src.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, NullPointerException {

        // check access token
        try {
            String header = request.getHeader("Authorization");
            String accessToken = header.replace("Bearer ", "");

            if (accessToken != null) {
                Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(accessToken).getBody();
                if (claims.getIssuer().equals(jwtIssuer) && new Date(   ).before(claims.getExpiration())) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims.get("id"), null, Collections.emptyList()));
                    TestContextHolder.getContext().setUserId((Integer) claims.get("id"));
                }
            }

        } catch (NullPointerException e) {
            // 헤더 없을 경우
            SecurityContextHolder.clearContext();
            throw new UsernameFromTokenException("[JWT Token Filter Error]: User Not Found. Please check header.");
        } catch (ExpiredJwtException e) {
            // jwt 만료
            SecurityContextHolder.clearContext();
            throw new UsernameFromTokenException("[JWT Token Filter Error]: User Not Found. AccessToken Expired. ");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter (HttpServletRequest request) {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
        excludeUrlPatterns.add("/error");
        excludeUrlPatterns.add("/webTest");
        excludeUrlPatterns.add("/");
        excludeUrlPatterns.add("/login");
        excludeUrlPatterns.add("/member");
        excludeUrlPatterns.add("/webjars/**");
        excludeUrlPatterns.add("/swagger-ui.html");
        excludeUrlPatterns.add("/**/swagger-ui/**");
        excludeUrlPatterns.add("/swagger-resources/**");
        excludeUrlPatterns.add("/api-docs/**");
        excludeUrlPatterns.add("/v2/api-docs");

        return excludeUrlPatterns.stream().anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}