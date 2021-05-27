package web.controller;

import core.controller.interfaces.BookServiceInterface;
import core.exceptions.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.BookConverter;
import web.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookWebController {
    public static final Logger log= LoggerFactory.getLogger(BookWebController.class);

    @Autowired
    private BookServiceInterface bookService;

    @Autowired
    private BookConverter bookConverter;


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    List<BookDto> getBooks() {
        log.trace("getBooks: method entered");
        log.trace("getBooks: method exit");
        return new ArrayList<>(bookConverter.convertModelsToDtos(bookService.getAll()));
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDto saveBook(@RequestBody BookDto bookDto) throws ControllerException {
        log.trace("saveBook: method entered: bookDto = {}",bookDto);

        BookDto returnValue = bookConverter.convertModelToDto(
                    bookService.add(bookConverter.convertDtoToModel(bookDto)));

        log.trace("saveBook: method exit: returnValue={}",returnValue);
        return returnValue;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    BookDto updateBook(@PathVariable Long id,
                             @RequestBody BookDto bookDto) {

        log.trace("updateBook: method entered: bookDto={}",bookDto);

        BookDto returnValue = bookConverter.convertModelToDto(
                bookService.update(bookConverter.convertDtoToModel(bookDto)));

        log.trace("updateBook: method exit: returnValue={}",returnValue);

        return returnValue;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBook(@PathVariable Long id) throws ControllerException {

        log.trace("deleteBook: entered method: id={}",id);

        bookService.remove(id);

        log.trace("deleteBook: exit method");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
