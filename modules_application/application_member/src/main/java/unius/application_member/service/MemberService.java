package unius.application_member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.application_member.dto.InitializeUserInfoDto;
import unius.application_member.mapper.GetMyUserInfoMapper;
import unius.domain_bookshelf.domain.Bookshelf;
import unius.domain_bookshelf.service.BookshelfService;
import unius.domain_user.domain.User;
import unius.domain_user.service.UserService;
import unius.system_exception.component.DomainValidator;

import java.util.Objects;

import static unius.core_user.type.UserState.INCOMPLETE;
import static unius.core_user.type.UserState.VERIFIED;
import static unius.system_exception.type.ExceptionType.ALREADY_EXIST_BOOKSHELF;
import static unius.system_exception.type.ExceptionType.INVALID_USER;

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
        String uuid = bookshelfService.create(user, request.getNickname(), request.isOpen());

        return new InitializeUserInfoDto.Response(uuid);
    }
}
