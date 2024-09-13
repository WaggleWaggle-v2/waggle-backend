package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_oauth.service.OAuthInfoService;
import unius.domain_user.domain.User;
import unius.system_jwt.dto.GenerateTokenDto;
import unius.system_jwt.service.TokenService;
import unius.system_oauth.service.OAuthService;

import static unius.domain_oauth.type.PlatformType.GOOGLE;
import static unius.domain_oauth.type.PlatformType.KAKAO;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String GRANT_TYPE = "Bearer ";

    private final TokenService tokenService;
    private final OAuthInfoService oAuthInfoService;
    private final OAuthService oAuthService;

    @Transactional
    public String googleLogin (String code) {
        String oAuthId = oAuthService.getGoogleAuthCode(code);
        User user = oAuthInfoService.login(oAuthId, GOOGLE);

        GenerateTokenDto.Response token = tokenService.generateToken(user.getId(), user.getUserState());

        return GRANT_TYPE + token.getAccessToken();
    }

    @Transactional
    public String kakaoLogin (String code) {
        String oAuthId = oAuthService.getKakaoAuthCode(code);
        User user = oAuthInfoService.login(oAuthId, KAKAO);

        GenerateTokenDto.Response token = tokenService.generateToken(user.getId(), user.getUserState());

        return GRANT_TYPE + token.getAccessToken();
    }
}
