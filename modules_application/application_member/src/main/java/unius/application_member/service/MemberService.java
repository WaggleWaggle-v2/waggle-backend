package unius.application_member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.application_member.mapper.GetMyUserInfoMapper;
import unius.domain_user.domain.User;
import unius.domain_user.service.UserService;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserService userService;

    public GetMyUserInfoDto.Response getMyUserInfo(Long userId) {
        User user = userService.get(userId);

        return GetMyUserInfoMapper.INSTANCE.toDto(user);
    }
}
