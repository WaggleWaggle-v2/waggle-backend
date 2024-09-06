package unius.domain_user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import unius.domain_user.domain.User;

import static unius.domain_user.domain.QUser.user;

@Repository
@ComponentScan("unius.independent_jpa")
@RequiredArgsConstructor
public class UserRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    public User getUserInfo(Long id) {
        BooleanExpression condition = user.id.eq(id);

        return jpaQueryFactory
                .selectFrom(user)
                .where(condition)
                .fetchOne();
    }
}
