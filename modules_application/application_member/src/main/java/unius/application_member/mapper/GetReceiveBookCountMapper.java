package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetReceiveBookCountDto;
import unius.domain_bookshelf.domain.Bookshelf;

@Mapper
public interface GetReceiveBookCountMapper {
    GetReceiveBookCountMapper INSTANCE = Mappers.getMapper(GetReceiveBookCountMapper.class);

    @Mapping(source = "count", target = "receiveCount")
    GetReceiveBookCountDto.Response toDto(Bookshelf bookshelf);
}
