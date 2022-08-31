package in.cdac.university.apigateway.config;

import in.cdac.university.apigateway.response.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

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

    public UserDetail getUserDetail(String token) {
        UserDetail userDetail = new UserDetail();
        try {
            Claims allClaimsFromToken = this.getAllClaimsFromToken(token);
            int universityId = Optional.ofNullable(allClaimsFromToken.get("universityId"))
                                        .map(value -> Integer.valueOf(value.toString()))
                                        .orElse(-1);
            userDetail.setUniversityId(universityId);

            int userId = Optional.ofNullable(allClaimsFromToken.get("userId"))
                                .map(value -> Integer.valueOf(value.toString()))
                                .orElse(-1);
            userDetail.setUserId(userId);

            int applicationType = Optional.ofNullable(allClaimsFromToken.get("applicationType"))
                                        .map(value -> Integer.valueOf(value.toString()))
                                        .orElse(-1);
            userDetail.setApplicationType(applicationType);
        } catch (ExpiredJwtException e) {
            log.info("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetail;
    }

    public long getTTLForToken(String token) {
        long diff = 0;
        try {
            Date expirationDate = this.getAllClaimsFromToken(token)
                                        .getExpiration();
            diff = expirationDate.getTime() - System.currentTimeMillis();
            if (diff < 0)
                diff = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
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
