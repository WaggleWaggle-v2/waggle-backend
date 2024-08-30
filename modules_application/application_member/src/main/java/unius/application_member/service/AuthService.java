package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_oauth.dto.CreateOAuthDto;
import unius.domain_oauth.service.OAuthInfoService;
import unius.domain_user.domain.User;
import unius.domain_user.dto.CreateUserDto;
import unius.domain_user.service.UserService;
import unius.system_jwt.dto.GenerateTokenDto;
import unius.system_jwt.service.TokenService;
import unius.system_oauth.service.OAuthService;

import static unius.domain_oauth.type.PlatformType.GOOGLE;
import static unius.domain_user.type.UserState.INCOMPLETE;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final OAuthInfoService oAuthInfoService;
    private final OAuthService oAuthService;

    @Transactional
    public String googleLogin (String code) {
        String oAuthId = oAuthService.getGoogleAuthCode(code);
        User user = userService.create(new CreateUserDto.Request(INCOMPLETE));
        oAuthInfoService.create(new CreateOAuthDto.Request(oAuthId, GOOGLE, user));

        GenerateTokenDto.Response token = tokenService.generateToken(new GenerateTokenDto.Request(user.getId(), INCOMPLETE.getDescription()));

        return token.getAccessToken();
    }
}
