package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetBookInfoDto;
import unius.domain_book.domain.Book;

@Mapper
public interface GetBookInfoMapper {
    GetBookInfoMapper INSTANCE = Mappers.getMapper(GetBookInfoMapper.class);

    @Mapping(target = "description", expression = "java(book.getDescription())")
    @Mapping(target = "senderNickname", expression = "java(book.getNickname())")
    @Mapping(target = "createdAt", expression = "java(book.getCreatedAt())")
    @Mapping(target = "bookImageUrl", expression = "java(book.getBookImageUrl())")
    GetBookInfoDto.Response toDto(Book book, String receiverNickname, boolean isMine, boolean isLock);
}
