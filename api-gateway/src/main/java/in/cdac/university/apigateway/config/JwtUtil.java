package in.cdac.university.apigateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

    public int getApplicationType(String token) {
        try {
            return Integer.parseInt(this.getAllClaimsFromToken(token).get("applicationType").toString());
        } catch (ExpiredJwtException e) {
            log.info("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getUserId(String token) {
        try {
            return Integer.parseInt(this.getAllClaimsFromToken(token).get("userId").toString());
        } catch (ExpiredJwtException e) {
            log.info("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isTokenExpired(String token) {
        try {
            return this.getAllClaimsFromToken(token)
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException e) {
            log.info("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
