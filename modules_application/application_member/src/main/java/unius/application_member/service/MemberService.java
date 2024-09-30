package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unius.application_member.dto.*;
import unius.application_member.mapper.*;
import unius.domain_book.domain.Book;
import unius.domain_book.service.BookService;
import unius.domain_book.type.BookState;
import unius.domain_book_list.domain.BookList;
import unius.domain_book_list.service.BookListService;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.service.BookshelfService;
import unius.domain_bookshelf.type.BookshelfType;
import unius.domain_user.domain.User;
import unius.domain_user.service.UserService;
import unius.independent_s3.service.S3Service;
import unius.schema.bookCounter.BookCounter;
import unius.schema.postCounter.PostCounter;
import unius.system_book_counter.component.BookCounterProducer;
import unius.system_exception.component.DomainValidator;
import unius.system_exception.exception.WaggleException;
import unius.system_post_counter.component.PostCounterProducer;

import java.util.List;
import java.util.Objects;

import static unius.core_user.type.UserState.INCOMPLETE;
import static unius.core_user.type.UserState.VERIFIED;
import static unius.domain_bookshelf.type.BookshelfState.ACTIVE;
import static unius.system_exception.type.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final DomainValidator<User> userValidator;
    private final DomainValidator<Bookshelf> bookshelfValidator;
    private final DomainValidator<Book> bookValidator;
    private final DomainValidator<BookList> bookListValidator;

    private final UserService userService;
    private final BookService bookService;
    private final BookListService bookListService;
    private final BookshelfService bookshelfService;
    private final S3Service s3Service;

    private static final String BOOK_DOMAIN = "book";
    private static final String ORDER_ASC = "asc";
    private static final String ORDER_DESC = "desc";
    private static final String BOOK_COUNTER_TOPIC = "book_counter";
    private static final String POST_COUNTER_TOPIC = "post_counter";

    private final BookCounterProducer bookCounterProducer;
    private final PostCounterProducer postCounterProducer;

    public GetMyUserInfoDto.Response getMyUserInfo(String userId) {
        User user = userValidator.of(userService.get(userId, VERIFIED, INCOMPLETE))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        String nickname = null;

        if(user.getUserState() == VERIFIED) {
            Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .getOrThrow();
            nickname = bookshelf.getNickname();
        }

        return GetMyUserInfoMapper.INSTANCE.toDto(user, nickname);
    }

    @Transactional
    public void initializeUserInfo(
            String userId,
            InitializeUserInfoDto.Request request) {
        User user = userValidator.of(userService.get(userId, INCOMPLETE))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        bookshelfValidator.of(bookshelfService.get(userId))
                .validate(Objects::isNull, ALREADY_EXIST_BOOKSHELF)
                .execute();

        userService.setUserState(user, VERIFIED);
        bookshelfService.create(user, request.getNickname(), request.getIsOpen());
    }

    @Transactional
    public SetUserNicknameDto.Response setUserNickname(String userId, SetUserNicknameDto.Request request) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .validate(bs -> bs.getUser().equals(user), HAVE_NO_PERMISSION)
                .getOrThrow();

        String nickname = bookshelfService.setNickname(bookshelf, request.getNickname());

        return new SetUserNicknameDto.Response(nickname);
    }

    @Transactional
    public void setBookshelfRevelation(String userId, SetBookshelfRevelationDto.Request request) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .validate(bs -> bs.getUser().equals(user), HAVE_NO_PERMISSION)
                .getOrThrow();

        bookshelfService.setIsOpen(bookshelf, request.getIsOpen());
    }

    @Transactional
    public void setBookshelfBackground(String userId, SetBookshelfBackgroundDto.Request request) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .validate(bs -> bs.getUser().equals(user), HAVE_NO_PERMISSION)
                .getOrThrow();

        bookshelfService.setProfileImage(bookshelf, request.getNumber());
    }

    @Transactional
    public void setBookshelfTheme(String userId, SetBookshelfThemeDto.Request request) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .validate(bs -> bs.getUser().equals(user), HAVE_NO_PERMISSION)
                .getOrThrow();

        try {
            bookshelfService.setTheme(bookshelf, Enum.valueOf(BookshelfType.class, request.getTheme()));
        } catch (IllegalArgumentException e) {
            throw new WaggleException(MISMATCH_ARGUMENT);
        }

    }

    @Transactional
    public void setBookshelfIntroduction(String userId, SetBookshelfIntroductionDto.Request request) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .validate(bs -> bs.getUser().equals(user), HAVE_NO_PERMISSION)
                .getOrThrow();

        bookshelfService.setIntroduction(bookshelf, request.getIntroduction());
    }

    public GetBookshelfInfoDto.Response getBookshelfInfo(String userId, String uuid) {
        Bookshelf bookshelf;

        if(userId == null || userId.isEmpty()) {
            bookshelf = bookshelfValidator.of(bookshelfService.get(uuid, ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .validate(Bookshelf::isOpen, HAVE_NO_PERMISSION)
                    .getOrThrow();
        } else {
            userValidator.of(userService.get(userId, VERIFIED))
                    .validate(Objects::nonNull, INVALID_USER)
                    .getOrThrow();

            bookshelf = bookshelfValidator.of(bookshelfService.get(uuid, ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .validate(bs -> bs.isOpen() || bs.getId().equals(userId), HAVE_NO_PERMISSION)
                    .getOrThrow();
        }

        return GetBookshelfInfoMapper.INSTANCE.toDto(bookshelf);
    }

    public List<GetRandomBookshelfInfoDto.Response> getRandomBookshelfInfo() {
        List<Bookshelf> bookshelfList = bookshelfService.getRandom();

        return GetRandomBookshelfInfoMapper.INSTANCE.toDtoList(bookshelfList);
    }

    @Transactional
    public CreateBookDto.Response createBook(String userId, MultipartFile bookImage, CreateBookDto.Request request) {
        User user;
        Bookshelf targetBookshelf;
        boolean isMember = !(userId == null || userId.isEmpty());

        if(!isMember) {
            user = null;
            targetBookshelf = bookshelfValidator.of(bookshelfService.get(request.getBookshelfId(), ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .validate(Bookshelf::isOpen, HAVE_NO_PERMISSION)
                    .getOrThrow();
        } else {
            user = userValidator.of(userService.get(userId, VERIFIED))
                    .validate(Objects::nonNull, INVALID_USER)
                    .getOrThrow();

            targetBookshelf = bookshelfValidator.of(bookshelfService.get(request.getBookshelfId(), ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .validate(bs -> bs.isOpen() || bs.getUser().equals(user), HAVE_NO_PERMISSION)
                    .getOrThrow();
        }

        String bookImageUrl = s3Service.uploadFile(bookImage, BOOK_DOMAIN);

        if(isMember && user != null && !request.isOpen()) {
            throw new WaggleException(MISMATCH_ARGUMENT);
        }
        Book book = bookService.create(targetBookshelf, request.getNickname(), request.getDescription(), bookImageUrl, request.isOpen(), request.getBookType());

        if(isMember && user != null) {
            bookListService.create(user, book);
            postCounterProducer.sendMessage(POST_COUNTER_TOPIC, new PostCounter(user.getId(), 1L));
        }
        bookCounterProducer.sendMessage(BOOK_COUNTER_TOPIC, new BookCounter(targetBookshelf.getId(), 1L));

        return CreateBookMapper.INSTANCE.toDto(book, targetBookshelf.getId(), bookImageUrl);
    }

    public List<GetBookshelfBookListDto.Response> getBookshelfBookList(String userId, String uuid, Long cursorId) {
        boolean isMember = !(userId == null || userId.isEmpty());

        Bookshelf targetBookshelf;

        if(!isMember) {
            targetBookshelf = bookshelfValidator.of(bookshelfService.get(uuid, ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .validate(Bookshelf::isOpen, HAVE_NO_PERMISSION)
                    .getOrThrow();
        } else {
            User user = userValidator.of(userService.get(userId, VERIFIED))
                    .validate(Objects::nonNull, INVALID_USER)
                    .getOrThrow();

            targetBookshelf = bookshelfValidator.of(bookshelfService.get(uuid, ACTIVE))
                    .validate(Objects::nonNull, INVALID_BOOKSHELF)
                    .validate(bs -> bs.getUser().equals(user) || bs.isOpen(), HAVE_NO_PERMISSION)
                    .getOrThrow();
        }

        List<Book> bookList = bookService.getBookshelfBookList(targetBookshelf, cursorId);

        return GetBookshelfBookListMapper.INSTANCE.toDtoList(bookList);
    }

    public GetBookInfoDto.Response getBookDetail(String userId, Long bookId) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Book book = bookValidator.of(bookService.getBook(bookId, BookState.ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOK)
                .getOrThrow();

        if(book.isOpen()) {
            return GetBookInfoMapper.INSTANCE.toDto(book, false);
        }

        BookList bookList;

        try {
            bookList = bookListValidator.of(bookListService.getBookList(bookId))
                    .validate(Objects::nonNull, INVALID_BOOK)
                    .validate(bl -> bl.getBook().isOpen() || bl.getUser().equals(user), HAVE_NO_PERMISSION)
                    .getOrThrow();

        } catch (WaggleException e) {
            if(e.getStatusCode() == 403) {
                return new GetBookInfoDto.Response(true, null, null);
            } else {
                throw e;
            }
        }

        return GetBookInfoMapper.INSTANCE.toDto(bookList, false);
    }

    @Transactional
    public void deleteBook(String userId, Long bookId) {
        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Book book = bookValidator.of(bookService.getBook(bookId, BookState.ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOK)
                .getOrThrow();

        String targetBookshelfId = book.getBookshelf().getId();
        Bookshelf targetBookshelf = bookshelfValidator.of(bookshelfService.get(targetBookshelfId))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        try {
            bookListValidator.of(bookListService.getBookList(user, book))
                    .validate(Objects::nonNull, HAVE_NO_PERMISSION)
                    .execute();
        } catch (WaggleException e) {
            // Book은 유효하지만, BookList에는 유효하지 않은 경우 → 비회원이 남긴 경우

            if(e.getStatusCode() == 403) {
                bookCounterProducer.sendMessage(BOOK_COUNTER_TOPIC, new BookCounter(targetBookshelfId, -1L));
                bookService.setBookState(book, BookState.WITHDRAW);
                return;
            } else {
                throw e;
            }
        }

        bookCounterProducer.sendMessage(BOOK_COUNTER_TOPIC, new BookCounter(targetBookshelfId, -1L));
        postCounterProducer.sendMessage(POST_COUNTER_TOPIC, new PostCounter(userId, -1L));
        bookService.setBookState(book, BookState.WITHDRAW);
    }

    public List<GetMySendBookDto.Response> getMySendBookList(String userId, Long cursorId, String order) {
        if(!order.equals(ORDER_ASC) && !order.equals(ORDER_DESC)) {
            throw new WaggleException(MISMATCH_ARGUMENT);
        }

        User user = userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        List<BookList> bookLists = bookListService.getMySendBookList(user, cursorId, order);

        if(bookLists == null) {
            return null;
        } else {
            return GetMySendBookListMapper.INSTANCE.toDtoList(bookLists);
        }
    }

    public List<GetMyReceiveBookDto.Response> getMyReceiveBookList(String userId, Long cursorId, String order) {
        if(!order.equals(ORDER_ASC) && !order.equals(ORDER_DESC)) {
            throw new WaggleException(MISMATCH_ARGUMENT);
        }

        userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .execute();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        List<Book> bookList = bookService.getMyReceiveBookList(bookshelf, cursorId, order);

        if(bookList == null) {
            return null;
        } else {
            return GetMyReceiveBookListMapper.INSTANCE.toDtoList(bookList);
        }
    }
}
