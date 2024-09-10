package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetBookshelfInfoDto;
import unius.domain_bookshelf.domain.Bookshelf;

@Mapper
public interface GetBookshelfInfoMapper {
    GetBookshelfInfoMapper INSTANCE = Mappers.getMapper(GetBookshelfInfoMapper.class);

    @Mapping(target = "bookshelfType", expression = "java(bookshelf.getBookshelfType().getDescription())")
    GetBookshelfInfoDto.Response toDto(Bookshelf bookshelf);
}
