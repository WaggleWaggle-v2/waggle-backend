package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetBookshelfBookListDto;
import unius.domain_book.domain.Book;

import java.util.List;

@Mapper
public interface GetBookshelfBookListMapper {
    GetBookshelfBookListMapper INSTANCE = Mappers.getMapper(GetBookshelfBookListMapper.class);

    @Mapping(target = "bookType", expression = "java(book.getBookType().getDescription())")
    GetBookshelfBookListDto.Response toDto(Book book);

    List<GetBookshelfBookListDto.Response> toDtoList(List<Book> bookList);
}
