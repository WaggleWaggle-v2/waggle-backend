package unius.domain_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.core_enum.util.EnumUtils;
import unius.domain_book.domain.Book;
import unius.domain_book.repository.BookRepository;
import unius.domain_book.type.BookType;
import unius.domain_bookshelf.domain.Bookshelf;

import static unius.domain_book.type.BookState.ACTIVE;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book create(
            Bookshelf bookshelf,
            String nickname,
            String title,
            String description,
            String bookImageUrl,
            boolean isOpen,
            String bookType) {

        Book book = Book.builder()
                .bookshelf(bookshelf)
                .nickname(nickname)
                .title(title)
                .description(description)
                .bookImageUrl(bookImageUrl)
                .isOpen(isOpen)
                .bookState(ACTIVE)
                .bookType(EnumUtils.getEnumValue(BookType.class, bookType))
                .build();

        bookRepository.saveAndFlush(book);

        return book;
    }
}
