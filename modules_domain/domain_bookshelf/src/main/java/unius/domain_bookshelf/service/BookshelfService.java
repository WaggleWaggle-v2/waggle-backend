package unius.domain_bookshelf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.core_uuid.util.UuidUtils;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.repository.BookshelfRepository;
import unius.domain_bookshelf.repository.BookshelfRepositoryQuerydsl;
import unius.domain_bookshelf.type.BookshelfState;
import unius.domain_user.domain.User;

import static unius.domain_bookshelf.type.BookshelfState.ACTIVE;
import static unius.domain_bookshelf.type.BookshelfType.WHITE;

@Service
@RequiredArgsConstructor
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final BookshelfRepositoryQuerydsl bookshelfRepositoryQuerydsl;

    public String create(User user, String nickname, boolean isOpen) {
        String uuid = UuidUtils.generateUuid();

        Bookshelf bookshelf = Bookshelf.builder()
                .user(user)
                .nickname(nickname)
                .isOpen(isOpen)
                .bookshelfType(WHITE)
                .bookshelfState(ACTIVE)
                .count(0L)
                .uuid(uuid)
                .build();

        bookshelfRepository.save(bookshelf);

        return uuid;
    }

    public Bookshelf get(Long bookshelfId, BookshelfState... bookshelfStates) {
        return bookshelfRepositoryQuerydsl.getBookshelf(bookshelfId, bookshelfStates);
    }

    public String setNickname(Bookshelf bookshelf, String nickname) {
        bookshelfRepositoryQuerydsl.setNickname(bookshelf, nickname);

        return nickname;
    }
}
