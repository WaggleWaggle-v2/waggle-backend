package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.application_member.dto.InitializeUserInfoDto;
import unius.application_member.dto.SetBookshelfRevelationDto;
import unius.application_member.dto.SetUserNicknameDto;
import unius.application_member.mapper.GetMyUserInfoMapper;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.service.BookshelfService;
import unius.domain_user.domain.User;
import unius.domain_user.service.UserService;
import unius.system_exception.component.DomainValidator;

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

    public GetMyUserInfoDto.Response getMyUserInfo(Long userId) {
        User user = userValidator.of(userService.get(userId, VERIFIED, INCOMPLETE))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        return GetMyUserInfoMapper.INSTANCE.toDto(user);
    }

    @Transactional
    public InitializeUserInfoDto.Response initializeUserInfo(
            Long userId,
            InitializeUserInfoDto.Request request) {
        User user = userValidator.of(userService.get(userId, INCOMPLETE))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        bookshelfValidator.of(bookshelfService.get(userId))
                .validate(Objects::isNull, ALREADY_EXIST_BOOKSHELF)
                .getOrThrow();

        userService.setUserState(user, VERIFIED);
        String uuid = bookshelfService.create(user, request.getNickname(), request.getIsOpen());

        return new InitializeUserInfoDto.Response(uuid);
    }

    @Transactional
    public SetUserNicknameDto.Response setUserNickname(Long userId, SetUserNicknameDto.Request request) {
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
    public void setBookshelfRevelation(Long userId, SetBookshelfRevelationDto.Request request) {
        userValidator.of(userService.get(userId, VERIFIED))
                .validate(Objects::nonNull, INVALID_USER)
                .getOrThrow();

        Bookshelf bookshelf = bookshelfValidator.of(bookshelfService.get(userId, ACTIVE))
                .validate(Objects::nonNull, INVALID_BOOKSHELF)
                .getOrThrow();

        bookshelfService.setIsOpen(bookshelf, request.getIsOpen());
    }
}
