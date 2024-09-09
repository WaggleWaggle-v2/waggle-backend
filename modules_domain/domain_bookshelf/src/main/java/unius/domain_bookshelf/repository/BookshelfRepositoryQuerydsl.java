package unius.domain_bookshelf.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.type.BookshelfState;

import static com.querydsl.core.types.dsl.Expressions.FALSE;
import static unius.domain_bookshelf.domain.QBookshelf.bookshelf;
import static unius.domain_bookshelf.type.BookshelfState.ACTIVE;

@Repository
@RequiredArgsConstructor
public class BookshelfRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public Bookshelf getBookshelf(Long id, BookshelfState... bookshelfStates) {
        BooleanExpression condition;

        if(bookshelfStates == null || bookshelfStates.length == 0) {
            condition = bookshelf.id.eq(id);
        } else {
            condition = FALSE;

            for(BookshelfState bookshelfState : bookshelfStates) {
                condition = condition.or(bookshelf.bookshelfState.eq(bookshelfState));
            }
            condition = condition.and(bookshelf.id.eq(id));
        }

        return jpaQueryFactory
                .selectFrom(bookshelf)
                .where(condition)
                .fetchOne();
    }

    public void setNickname(Bookshelf currentBookshelf, String nickname) {
        BooleanExpression condition = bookshelf.eq(currentBookshelf)
                .and(bookshelf.bookshelfState.eq(ACTIVE));

        jpaQueryFactory.update(bookshelf)
                .set(bookshelf.nickname, nickname)
                .where(condition)
                .execute();
    }
}
