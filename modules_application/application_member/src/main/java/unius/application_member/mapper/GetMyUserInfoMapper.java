package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetMyUserInfoDto;
import unius.domain_user.domain.User;

@Mapper
public interface GetMyUserInfoMapper {
    GetMyUserInfoMapper INSTANCE = Mappers.getMapper(GetMyUserInfoMapper.class);

    @Mapping(target = "userState", expression = "java(user.getUserState().getDescription())")
    @Mapping(source = "nickname", target = "nickname")
    GetMyUserInfoDto.Response toDto(User user, String nickname);
}
