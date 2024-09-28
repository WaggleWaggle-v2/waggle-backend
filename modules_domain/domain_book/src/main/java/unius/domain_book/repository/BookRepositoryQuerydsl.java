package unius.domain_book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unius.domain_book.domain.Book;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_user.domain.User;

import java.util.List;

import static unius.domain_book.domain.QBook.book;
import static unius.domain_book.type.BookState.ACTIVE;

@Repository
@RequiredArgsConstructor
public class BookRepositoryQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Book> getBookshelfBookList(User currentUser, Bookshelf currentBookshelf, Long cursorId) {
        BooleanExpression baseCondition;
        BooleanExpression cursorCondition = cursorId == null ? null : book.id.lt(cursorId);

        boolean isMember = (currentUser != null);

        if(!isMember) {
            baseCondition = book.bookshelf.eq(currentBookshelf)
                    .and(book.bookState.eq(ACTIVE))
                    .and(book.isOpen.eq(true));
        } else {
            baseCondition = book.bookshelf.eq(currentBookshelf)
                    .and(book.bookState.eq(ACTIVE))
                    .and(book.isOpen.eq(true)
                            .or(book.bookshelf.user.eq(currentUser)));
        }

        BooleanExpression condition = baseCondition.and(cursorCondition);

        return jpaQueryFactory.selectFrom(book)
                .where(condition)
                .orderBy(book.id.desc())
                .limit(20)
                .fetch();
    }
}
