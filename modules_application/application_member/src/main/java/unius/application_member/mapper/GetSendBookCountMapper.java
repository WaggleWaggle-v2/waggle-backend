package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetSendBookCountDto;
import unius.domain_user.domain.User;

@Mapper
public interface GetSendBookCountMapper {
    GetSendBookCountMapper INSTANCE = Mappers.getMapper(GetSendBookCountMapper.class);

    @Mapping(source = "postCount", target = "sendCount")
    GetSendBookCountDto.Response toDto(User user);
}
