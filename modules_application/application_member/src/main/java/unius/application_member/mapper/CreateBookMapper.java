package unius.application_member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import unius.application_member.dto.CreateBookDto;
import unius.domain_book.domain.Book;

@Mapper
public interface CreateBookMapper {
    CreateBookMapper INSTANCE = Mappers.getMapper(CreateBookMapper.class);

    @Mapping(target = "bookType", expression = "java(book.getBookType().getDescription())")
    CreateBookDto.Response toDto(Book book, String bookshelfId, String bookImageUrl);
}
