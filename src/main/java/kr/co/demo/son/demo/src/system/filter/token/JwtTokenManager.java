package kr.co.demo.son.demo.src.system.filter.token;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.demo.son.demo.src.dto.login.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenManager {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    public final static Long ACCESS_TOKEN_TIME_OUT = 1000L * 60  * 60; // 1시간

    public String generateToken(TokenDto tokenDto) {
        return newToken(tokenDto, ACCESS_TOKEN_TIME_OUT); // 1일
    }

    public String newToken (TokenDto token, Long expireTime) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.builder()
                .setIssuedAt(new Date())
                .claim("id", token.getId())
                .claim("email", token.getEmail())
                .claim("mobileNo", token.getMobileNo())
                .setIssuer(jwtIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }
}
