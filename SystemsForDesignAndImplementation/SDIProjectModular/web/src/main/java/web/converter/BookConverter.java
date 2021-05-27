package web.converter;

import core.model.Book;
import org.springframework.stereotype.Component;
import web.dto.BookDto;

/**
 * Created by radu.
 */
@Component
public class BookConverter extends BaseConverter<Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {
        Book book = Book.builder()
                .bookName(dto.getBookName())
                .bookPrice(dto.getBookPrice())
                .build();
        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto dto = BookDto.builder()
                .bookName(book.getBookName())
                .bookPrice(book.getBookPrice())
                .build();

        dto.setId(book.getId());
        return dto;
    }
}
