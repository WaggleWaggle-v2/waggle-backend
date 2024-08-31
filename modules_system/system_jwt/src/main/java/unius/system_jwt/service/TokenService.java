package unius.system_jwt.service;

import org.springframework.util.StringUtils;
import unius.core_user.type.UserState;
import unius.system_jwt.dto.GenerateTokenDto.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import unius.system_jwt.util.TokenUtils;

import java.util.Date;

import static unius.system_jwt.type.ClaimKey.KEY_STATE;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 7;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;

    @Value("${spring.jwt.secret}")
    private String jwtPrivateKey;

    public Response generateToken(Long id, UserState userState) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(id));
        claims.put(KEY_STATE.getDescription(), userState.getDescription());

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtPrivateKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtPrivateKey)
                .compact();

        return new Response(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        if(!StringUtils.hasText(token)) return false;

        token = TokenUtils.removeTokenHeader(token);
        Claims claims = TokenUtils.parseToken(token);

        if(claims.get(KEY_STATE.getDescription()) != "VERIFIED") return false;

        return claims.getExpiration().after(new Date());
    }

    public String getId(String token) {
        if(!StringUtils.hasText(token)) return null;
        token = TokenUtils.removeTokenHeader(token);

        Claims claims = TokenUtils.parseToken(token);

        return claims.getSubject();
    }
}
