package core.controller.implementations;

import core.controller.interfaces.BookServiceInterface;
import core.exceptions.ControllerException;
import core.model.BaseEntity;
import core.model.Book;
import core.repository.BookRepository;
import core.validators.BookControllerValidator;
import core.validators.domain_validators.BookDomainValidator;
import core.validators.domain_validators.ValidatorException;
import core.validators.id_validators.BookIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService implements BookServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(BookService.class);


    @Autowired
    private BookRepository repository;

    @Autowired
    private BookControllerValidator bookControllerValidator;

    @Autowired
    private BookDomainValidator bookDomainValidator;

    @Autowired
    private BookIdValidator idValidator;

    @Autowired
    private TransactionService transactionService;

    @Override
    public List<Book> getAll() {
        log.trace("getAll - method entered:");
        Iterable<Book> iterable = repository.findAll();

        log.trace("getAll - method exit:");
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());

    }

    @Override
    public Book create(String data) throws ControllerException {
        log.trace("create - method entered:");

        String[] dataString = data.split(" ");
        int price;
        Long id;

        try{
            price = new Integer(dataString[1]);
            id = new Long(dataString[2]);
        }
        catch (NumberFormatException e){ throw new ControllerException("No id/price found"); }
        catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }


        Book returnBook = new Book(dataString[0],price);
        returnBook.setId(id);
        log.trace("create - method exit: returnBook={}",returnBook);
        return returnBook;
    }

    @Override
    public Book add(Book addedBook) throws ControllerException {
        log.trace("add - method entered: item={}",addedBook);

        try {
            idValidator.validateNonExistence(addedBook);
            bookDomainValidator.validate(addedBook);
            bookControllerValidator.validateNonExistence(addedBook);
            repository.save(addedBook);
        } catch (ValidatorException e) {
            throw new ControllerException(e.getMessage());
        }

        log.trace("add - method exit: addedBook={}",addedBook);

        return addedBook;


    }

    @Override
    public void remove(Long bookID){
        log.trace("remove - method entered: bookId={}", bookID);

        Book newBook = new Book("-",0);
        newBook.setId(bookID);

        idValidator.validateExistence(newBook);
        transactionService.getAll().stream()
                .filter(transaction -> transaction.getBookId().equals(bookID))
                .forEach(transaction -> {
                    transactionService.remove(transaction.getId());
                });
        repository.delete(repository.findById(bookID).get());

        log.trace("remove - method exit:");
    }

    @Override
    public Book update(Book newBook){
        log.trace("update - method entered: book={}",newBook);

        idValidator.validateExistence(newBook);
        repository.save(newBook);
        log.trace("update - method exit: newBook={}",newBook);

        return newBook;

    }
//
//    @Override
//    public Iterable<Book> findAll(Sort sort) {
//        log.trace("findAll - method entered:");
//        List<Book> returnList  = repository.findAll();
//        returnList.sort(sort.getSortComparator());
//        log.trace("findAll - method exit:");
//        return returnList;
//    }


    @Override
    public List<Book> filter(Function<BaseEntity<Long>, Boolean> lambda) {
        log.trace("filter - method entered:");
        log.trace("filter - method exit:");
        return repository.findAll().stream().filter(lambda::apply).collect(Collectors.toList());

    }
}
