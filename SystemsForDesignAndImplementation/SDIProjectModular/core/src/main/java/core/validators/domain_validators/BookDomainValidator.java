package core.validators.domain_validators;

import core.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookDomainValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidatorException {
        if (entity.getBookName().equals("")) throw new ValidatorException("Validation Failure: Invalid book name");
        if (entity.getBookPrice() < 0) throw new ValidatorException("Validation Failure: Invalid book price");
    }
}
