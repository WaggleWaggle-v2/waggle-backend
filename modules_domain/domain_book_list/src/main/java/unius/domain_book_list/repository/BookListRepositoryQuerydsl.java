package unius.domain_book_list.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unius.domain_book_list.domain.BookList;
import unius.domain_user.domain.User;

import java.util.List;

import static unius.domain_book.domain.QBook.book;
import static unius.domain_book_list.domain.QBookList.bookList;
import static unius.domain_user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class BookListRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<BookList> getMySendBookList(User currentUser, Long cursorId, String order) {
        BooleanExpression baseCondition = bookList.user.eq(currentUser);
        BooleanExpression cursorCondition;

        switch (order.toLowerCase()) {
            case "asc" -> {
                cursorCondition = cursorId == null ? null : bookList.id.gt(cursorId);

                BooleanExpression condition = baseCondition.and(cursorCondition);

                return jpaQueryFactory.selectFrom(bookList)
                        .join(bookList.user, user)
                        .join(bookList.book, book)
                        .fetchJoin()
                        .where(condition)
                        .orderBy(bookList.id.asc())
                        .limit(10)
                        .fetch();
            }
            case "desc" -> {
                cursorCondition = cursorId == null ? null : bookList.id.lt(cursorId);

                BooleanExpression condition = baseCondition.and(cursorCondition);

                return jpaQueryFactory.selectFrom(bookList)
                        .join(bookList.user, user)
                        .join(bookList.book, book)
                        .fetchJoin()
                        .where(condition)
                        .orderBy(bookList.id.desc())
                        .limit(10)
                        .fetch();
            }
            default -> {
                return null;
            }
        }
    }
}
