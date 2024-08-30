package unius.domain_user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("unius.independent_jpa")
@RequiredArgsConstructor
public class UserRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

}
