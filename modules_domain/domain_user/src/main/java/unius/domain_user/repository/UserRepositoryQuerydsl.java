package unius.domain_user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import unius.core_user.type.UserState;
import unius.domain_user.domain.User;

import static com.querydsl.core.types.dsl.Expressions.FALSE;
import static unius.domain_user.domain.QUser.user;

@Repository
@ComponentScan("unius.independent_jpa")
@RequiredArgsConstructor
public class UserRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    public User getUserInfo(Long id, UserState... userStates) {
        BooleanExpression condition;

        if(userStates == null || userStates.length == 0) {
            condition = user.id.eq(id);
        } else {
            condition = FALSE;

            for(UserState userState : userStates) {
                condition = condition.or(user.userState.eq(userState));
            }
            condition = condition.and(user.id.eq(id));
        }

        return jpaQueryFactory
                .selectFrom(user)
                .where(condition)
                .fetchOne();
    }

    public void setUserState(User currentUser, UserState userState) {
        BooleanExpression condition = user.eq(currentUser);

        jpaQueryFactory.update(user)
                .set(user.userState, userState)
                .where(condition)
                .execute();
    }
}
