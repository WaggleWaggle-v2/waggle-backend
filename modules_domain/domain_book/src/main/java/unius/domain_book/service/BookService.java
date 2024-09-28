package unius.domain_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.core_enum.util.EnumUtils;
import unius.domain_book.domain.Book;
import unius.domain_book.repository.BookRepository;
import unius.domain_book.repository.BookRepositoryQuerydsl;
import unius.domain_book.type.BookType;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_user.domain.User;

import java.util.List;

import static unius.domain_book.type.BookState.ACTIVE;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookRepositoryQuerydsl bookRepositoryQuerydsl;

    public Book create(
            Bookshelf bookshelf,
            String nickname,
            String description,
            String bookImageUrl,
            boolean isOpen,
            String bookType) {

        Book book = Book.builder()
                .bookshelf(bookshelf)
                .nickname(nickname)
                .description(description)
                .bookImageUrl(bookImageUrl)
                .isOpen(isOpen)
                .bookState(ACTIVE)
                .bookType(EnumUtils.getEnumValue(BookType.class, bookType))
                .build();

        bookRepository.saveAndFlush(book);

        return book;
    }

    public List<Book> getBookshelfBookList(User user, Bookshelf bookshelf, Long cursorId) {
        return bookRepositoryQuerydsl.getBookshelfBookList(user, bookshelf, cursorId);
    }
}
