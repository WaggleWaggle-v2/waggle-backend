package unius.domain_bookshelf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.repository.BookshelfRepository;
import unius.domain_bookshelf.repository.BookshelfRepositoryQuerydsl;
import unius.domain_bookshelf.type.BookshelfState;
import unius.domain_bookshelf.type.BookshelfType;
import unius.domain_user.domain.User;

import java.util.List;

import static unius.domain_bookshelf.type.BookshelfState.ACTIVE;
import static unius.domain_bookshelf.type.BookshelfType.WHITE;

@Service
@RequiredArgsConstructor
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final BookshelfRepositoryQuerydsl bookshelfRepositoryQuerydsl;

    private final String BACKGROUND_BUCKET_PATH = "https://unius.s3.ap-northeast-2.amazonaws.com/bookshelf_image/WGWG-profile-Image-0";

    public void create(User user, String nickname, boolean isOpen) {
        String DEFAULT_BACKGROUND = BACKGROUND_BUCKET_PATH + "1.jpg";

        Bookshelf bookshelf = Bookshelf.builder()
                .user(user)
                .nickname(nickname)
                .isOpen(isOpen)
                .backgroundImageUrl(DEFAULT_BACKGROUND)
                .bookshelfType(WHITE)
                .bookshelfState(ACTIVE)
                .count(0L)
                .build();

        bookshelfRepository.save(bookshelf);
    }

    public Bookshelf get(String bookshelfId, BookshelfState... bookshelfStates) {
        return bookshelfRepositoryQuerydsl.getBookshelf(bookshelfId, bookshelfStates);
    }

    public List<Bookshelf> getRandom() {
        return bookshelfRepositoryQuerydsl.getRandomBookshelves();
    }

    public String setNickname(Bookshelf bookshelf, String nickname) {
        bookshelfRepositoryQuerydsl.setNickname(bookshelf, nickname);

        return nickname;
    }

    public void setIsOpen(Bookshelf bookshelf, Boolean isOpen) {
        bookshelfRepositoryQuerydsl.setIsOpen(bookshelf, isOpen);
    }

    public void setProfileImage(Bookshelf bookshelf, Integer num) {
        bookshelfRepositoryQuerydsl.setBackgroundImageUrl(bookshelf, BACKGROUND_BUCKET_PATH + num + ".jpg");
    }

    public void setTheme(Bookshelf bookshelf, BookshelfType bookshelfType) {
        bookshelfRepositoryQuerydsl.setBookshelfType(bookshelf, bookshelfType);
    }

    public void setIntroduction(Bookshelf bookshelf, String introduction) {
        bookshelfRepositoryQuerydsl.setIntroduction(bookshelf, introduction);
    }

    public void updateBookCounter(String bookshelfId, Long additionValue) {
        bookshelfRepositoryQuerydsl.updateBookCounter(bookshelfId, additionValue);
    }
}
