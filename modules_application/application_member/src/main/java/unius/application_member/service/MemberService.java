package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.application_member.dto.*;
import unius.application_member.mapper.GetBookshelfInfoMapper;
import unius.application_member.mapper.GetMyUserInfoMapper;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.service.BookshelfService;
import unius.domain_bookshelf.type.BookshelfType;
import unius.domain_user.domain.User;
import unius.domain_user.service.UserService;
import unius.system_exception.component.DomainValidator;
import unius.system_exception.exception.WaggleException;

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

    private final UserService userService;
    private final BookshelfService bookshelfService;

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
                .getOrThrow();

        userService.setUserState(user, VERIFIED);
        bookshelfService.create(user, request.getNickname(), request.getIsOpen());
    }

    @Transactional
    public SetUserNicknameDto.Response setUserNickname(String userId, SetUserNicknameDto.Request request) {
        userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        String nickname = bookshelfService.setNickname(bookshelf, request.getNickname());

        return new SetUserNicknameDto.Response(nickname);
    }

    @Transactional
    public void setBookshelfRevelation(String userId, SetBookshelfRevelationDto.Request request) {
        userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        bookshelfService.setIsOpen(bookshelf, request.getIsOpen());
    }

    @Transactional
    public void setBookshelfBackground(String userId, SetBookshelfBackgroundDto.Request request) {
        userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        bookshelfService.setProfileImage(bookshelf, request.getNumber());
    }

    @Transactional
    public void setBookshelfTheme(String userId, SetBookshelfThemeDto.Request request) {
        userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        try {
            bookshelfService.setTheme(bookshelf, Enum.valueOf(BookshelfType.class, request.getTheme()));
        } catch (IllegalArgumentException e) {
            throw new WaggleException(MISMATCH_ARGUMENT);
        }

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
}
