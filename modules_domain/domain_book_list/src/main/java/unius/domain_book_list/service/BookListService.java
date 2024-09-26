package unius.domain_book_list.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_book.domain.Book;
import unius.domain_book_list.domain.BookList;
import unius.domain_book_list.repository.BookListRepository;
import unius.domain_user.domain.User;

@Service
@RequiredArgsConstructor
public class BookListService {

    private final BookListRepository bookListRepository;

    public void create(User user, Book book) {
        bookListRepository.save(BookList.builder()
                .user(user)
                .book(book)
                .build());
    }
}
