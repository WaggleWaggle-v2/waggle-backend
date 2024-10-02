package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.GetMyReceiveBookDto;
import unius.domain_book.domain.Book;

import java.util.List;

@Mapper
public interface GetMyReceiveBookListMapper {
    GetMyReceiveBookListMapper INSTANCE = Mappers.getMapper(GetMyReceiveBookListMapper.class);

    @Mapping(source = "id", target = "bookId")
    GetMyReceiveBookDto.Response toDto(Book book);

    List<GetMyReceiveBookDto.Response> toDtoList(List<Book> books);
}
