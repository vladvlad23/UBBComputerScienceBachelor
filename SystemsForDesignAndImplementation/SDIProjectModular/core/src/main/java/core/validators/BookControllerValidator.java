package core.validators;
import core.exceptions.ValidatorException;
import core.model.BaseEntity;
import core.model.Book;
import core.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class BookControllerValidator{
    @Autowired
    BookRepository repository;

    public void validateNonExistence(Book entity) throws ValidatorException {

        repository.findAll().stream()
                .filter(object-> object.getBookName().equals(entity.getBookName()) && object.getBookPrice() == entity.getBookPrice())
                .findAny().ifPresent(object->{throw new ValidatorException("Validation Failure: Book already exists.");});
    }

    public void validateExistence(Book entity) throws ValidatorException{
        try {
            Iterable<? extends BaseEntity<Long>> iterable = repository.findAll();

            StreamSupport.stream(iterable.spliterator(), false)
                    .filter(object->((Book)object).getBookName().equals(entity.getBookName()) && ((Book)object).getBookPrice() == entity.getBookPrice())
                    .findAny().ifPresent(object->{throw new RuntimeException("-");});
        }catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Book doesn't exist");
    }
}
