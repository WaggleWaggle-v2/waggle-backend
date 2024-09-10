package unius.domain_oauth.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_oauth.domain.OAuthInfo;
import unius.domain_oauth.repository.OAuthInfoRepository;
import unius.domain_oauth.repository.OAuthInfoRepositoryQuerydsl;
import unius.domain_oauth.type.PlatformType;
import unius.domain_user.domain.User;
import unius.domain_user.service.UserService;

@Service
@RequiredArgsConstructor
public class OAuthInfoService {

    private final EntityManager entityManager;

    private final OAuthInfoRepository oAuthInfoRepository;
    private final OAuthInfoRepositoryQuerydsl oAuthInfoRepositoryQuerydsl;

    private final UserService userService;

    public User login(String oAuthId, PlatformType platform) {
        OAuthInfo oAuthInfo = oAuthInfoRepositoryQuerydsl.getOAuthInfo(oAuthId, platform);
        User user;

        if(oAuthInfo == null) {
            user = userService.create();

            User proxyUser = entityManager.find(User.class, user.getId());

            oAuthInfo = OAuthInfo.builder()
                    .oauthId(oAuthId)
                    .platform(platform)
                    .user(proxyUser)
                    .build();

            oAuthInfoRepository.save(oAuthInfo);
        } else {
            user = this.getUser(oAuthId, platform);
        }

        return user;
    }

    public User getUser(String oAuthId, PlatformType platform) {
        return oAuthInfoRepositoryQuerydsl.getUser(oAuthId, platform);
    }
}
