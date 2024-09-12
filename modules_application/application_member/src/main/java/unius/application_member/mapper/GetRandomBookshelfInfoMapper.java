package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetRandomBookshelfInfoDto;
import unius.domain_bookshelf.domain.Bookshelf;

import java.util.List;

@Mapper
public interface GetRandomBookshelfInfoMapper {
    GetRandomBookshelfInfoMapper INSTANCE = Mappers.getMapper(GetRandomBookshelfInfoMapper.class);

    GetRandomBookshelfInfoDto.Response toDto(Bookshelf bookshelf);

    List<GetRandomBookshelfInfoDto.Response> toDtoList(List<Bookshelf> bookshelfList);
}
