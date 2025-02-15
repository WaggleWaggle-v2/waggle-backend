package unius.domain_book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unius.domain_book.domain.Book;
import unius.domain_book.type.BookState;
import unius.domain_bookshelf.domain.Bookshelf;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.FALSE;
import static unius.domain_book.domain.QBook.book;
import static unius.domain_book.type.BookState.ACTIVE;
import static unius.domain_bookshelf.domain.QBookshelf.bookshelf;
import static unius.domain_user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class BookRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public Book getBook(Long bookId, BookState... bookStates) {
        BooleanExpression condition;

        if(bookStates == null || bookStates.length == 0) {
            condition = book.id.eq(bookId);
        } else {
            condition = FALSE;

            for(BookState bookState : bookStates) {
                condition = condition.or(book.bookState.eq(bookState));
            }
            condition = condition.and(book.id.eq(bookId));
        }

        return jpaQueryFactory.selectFrom(book)
                .join(book.bookshelf, bookshelf).fetchJoin()
                .join(bookshelf.user, user).fetchJoin()
                .where(condition)
                .fetchOne();
    }

    public void setBookState(Book currentBook, BookState bookState) {
        BooleanExpression condition = book.eq(currentBook);

        jpaQueryFactory.update(book)
                .where(condition)
                .set(book.bookState, bookState)
                .execute();
    }

    public List<Book> getBookshelfBookList(Bookshelf currentBookshelf, Long cursorId) {
        BooleanExpression baseCondition = book.bookshelf.eq(currentBookshelf)
                .and(book.bookState.eq(ACTIVE));
        BooleanExpression cursorCondition = cursorId == null ? null : book.id.lt(cursorId);

        BooleanExpression condition = baseCondition.and(cursorCondition);

        return jpaQueryFactory.selectFrom(book)
                .where(condition)
                .orderBy(book.id.desc())
                .limit(20)
                .fetch();
    }

    public List<Book> getMyReceiveBookList(Bookshelf currentBookshelf, Long cursorId, String order) {
        BooleanExpression baseCondition = book.bookshelf.eq(currentBookshelf)
                .and(book.bookState.eq(ACTIVE));
        BooleanExpression cursorCondition;

        switch (order.toLowerCase()) {
            case "asc" -> {
                cursorCondition = cursorId == null ? null : book.id.gt(cursorId);

                BooleanExpression condition = baseCondition.and(cursorCondition);

                return jpaQueryFactory.selectFrom(book)
                        .where(condition)
                        .orderBy(book.id.asc())
                        .limit(10)
                        .fetch();
            }
            case "desc" -> {
                cursorCondition = cursorId == null ? null : book.id.lt(cursorId);

                BooleanExpression condition = baseCondition.and(cursorCondition);

                return jpaQueryFactory.selectFrom(book)
                        .where(condition)
                        .orderBy(book.id.desc())
                        .limit(10)
                        .fetch();
            }
            default -> {
                return null;
            }
        }
    }
}
