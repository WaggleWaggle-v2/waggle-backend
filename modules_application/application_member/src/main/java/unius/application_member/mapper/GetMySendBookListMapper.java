package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetMySendBookDto;
import unius.domain_book_list.domain.BookList;

import java.util.List;

@Mapper
public interface GetMySendBookListMapper {
    GetMySendBookListMapper INSTANCE = Mappers.getMapper(GetMySendBookListMapper.class);

    @Mapping(target = "nickname", expression = "java(bookList.getBook().getBookshelf().getNickname())")
    @Mapping(target = "description", expression = "java(bookList.getBook().getDescription())")
    @Mapping(target = "backgroundImageUrl", expression = "java(bookList.getBook().getBookshelf().getBackgroundImageUrl())")
    @Mapping(target = "bookId", expression = "java(bookList.getBook().getId())")
    GetMySendBookDto.Response toDto(BookList bookList);

    List<GetMySendBookDto.Response> toDtoList(List<BookList> bookList);
}
