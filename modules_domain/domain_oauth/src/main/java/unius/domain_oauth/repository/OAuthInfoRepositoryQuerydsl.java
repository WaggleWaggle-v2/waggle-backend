package unius.domain_oauth.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unius.domain_oauth.domain.OAuthInfo;
import unius.domain_oauth.type.PlatformType;
import unius.domain_user.domain.User;

import static unius.domain_oauth.domain.QOAuthInfo.oAuthInfo;

@Repository
@RequiredArgsConstructor
public class OAuthInfoRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public OAuthInfo getOAuthInfo(String oAuthId, PlatformType platform) {
        BooleanExpression condition = oAuthInfo.oauthId.eq(oAuthId).and(oAuthInfo.platform.eq(platform));

        return jpaQueryFactory.selectFrom(oAuthInfo)
                .where(condition)
                .fetchOne();
    }

    public User getUser(String oAuthId, PlatformType platform) {
        BooleanExpression condition = oAuthInfo.oauthId.eq(oAuthId).and(oAuthInfo.platform.eq(platform));

        OAuthInfo currentOAuthInfo = jpaQueryFactory.selectFrom(oAuthInfo)
                .where(condition)
                .fetchOne();

        if(currentOAuthInfo != null) {
            return currentOAuthInfo.getUser();
        } else {
            throw new RuntimeException();
        }
    }
}
