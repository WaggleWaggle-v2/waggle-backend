package unius.system_jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenUtils {

    public static String removeTokenHeader(String token) {
        return token.replace("Bearer ", "");
    }

    public static Claims parseToken(String token, String secretKey) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
