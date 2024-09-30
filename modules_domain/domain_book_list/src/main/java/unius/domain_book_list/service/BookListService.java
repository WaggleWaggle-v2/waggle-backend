package unius.domain_book_list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_book.domain.Book;
import unius.domain_book_list.domain.BookList;
import unius.domain_book_list.repository.BookListRepository;
import unius.domain_book_list.repository.BookListRepositoryQuerydsl;
import unius.domain_user.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookListService {

    private final BookListRepository bookListRepository;
    private final BookListRepositoryQuerydsl bookListRepositoryQuerydsl;

    public void create(User user, Book book) {
        bookListRepository.save(BookList.builder()
                .user(user)
                .book(book)
                .build());
    }

    public BookList getBookList(Long bookId) {
        return bookListRepositoryQuerydsl.getBookList(bookId);
    }

    public List<BookList> getMySendBookList(User user, Long cursorId, String order) {
        return bookListRepositoryQuerydsl.getMySendBookList(user, cursorId, order);
    }
}
