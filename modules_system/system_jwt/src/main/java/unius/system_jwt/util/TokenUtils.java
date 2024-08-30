package unius.system_jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class TokenUtils {
    @Value("${spring.jwt.secret}")
    private String jwtPrivateKey;

    public static String removeTokenHeader(String token) {
        return token.replace("Bearer ", "");
    }

    public static Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtPrivateKey).parseClaimsJws(token).getBody();
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
