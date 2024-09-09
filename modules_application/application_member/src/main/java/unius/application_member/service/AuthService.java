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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final OAuthInfoService oAuthInfoService;
    private final OAuthService oAuthService;

    @Transactional
    public String googleLogin (String code) {
        String oAuthId = oAuthService.getGoogleAuthCode(code);
        User user = oAuthInfoService.login(oAuthId, GOOGLE);

        GenerateTokenDto.Response token = tokenService.generateToken(user.getId(), user.getUserState());

        return token.getAccessToken();
    }
}
