package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetBookInfoDto;
import unius.domain_book.domain.Book;
import unius.domain_book_list.domain.BookList;

@Mapper
public interface GetBookInfoMapper {
    GetBookInfoMapper INSTANCE = Mappers.getMapper(GetBookInfoMapper.class);

    @Mapping(target = "description", expression = "java(bookList.getBook().getDescription())")
    @Mapping(target = "senderNickname", expression = "java(bookList.getBook().getNickname())")
    @Mapping(target = "createdAt", expression = "java(bookList.getBook().getCreatedAt())")
    @Mapping(target = "bookImageUrl", expression = "java(bookList.getBook().getBookImageUrl())")
    GetBookInfoDto.Response toDto(BookList bookList, String receiverNickname, boolean isLock);

    @Mapping(target = "description", expression = "java(book.getDescription())")
    @Mapping(target = "senderNickname", expression = "java(book.getNickname())")
    @Mapping(target = "createdAt", expression = "java(book.getCreatedAt())")
    @Mapping(target = "bookImageUrl", expression = "java(book.getBookImageUrl())")
    GetBookInfoDto.Response toDto(Book book, String receiverNickname, boolean isLock);
}
