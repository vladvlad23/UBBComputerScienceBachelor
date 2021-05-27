package core.validators;
import core.controller.implementations.BookService;
import core.controller.implementations.ClientService;
import core.exceptions.ValidatorException;
import core.model.BaseEntity;
import core.model.Transaction;
import core.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class TransactionControllerValidator{
    TransactionRepository repository;
    ClientService clientController;
    BookService bookController;

    public TransactionControllerValidator(TransactionRepository repository,
                                          ClientService clientController,
                                          BookService bookController) {
        this.repository = repository;
        this.bookController = bookController;
        this.clientController = clientController;
    }

    public void validateClientExistence(Transaction entity) throws ValidatorException {
        try {
            clientController.getAll().stream()
                    .map(BaseEntity::getId)
                    .filter(clientId -> clientId.equals(entity.getClientId()))
                    .findAny()
                    .ifPresent(item -> {
                        throw new RuntimeException("-");
                    });
        }
        catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Client doesn't exist.");
    }

    public void validateBookExistence(Transaction entity) throws ValidatorException{
        try {
            bookController.getAll().stream()
                    .map(BaseEntity::getId)
                    .filter(e -> e.equals(entity.getBookId()))
                    .findAny()
                    .ifPresent(item -> {
                        throw new RuntimeException("-");
                    });
        }
        catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Book doesn't exist.");
    }

    public void validateExistence(Transaction entity) throws ValidatorException{
        try {
            Iterable<? extends BaseEntity<Long>> iterable = repository.findAll();

            StreamSupport.stream(iterable.spliterator(), false)
                    .filter(object-> ((Transaction) object).getBookId().equals(entity.getBookId()) && ((Transaction) object).getClientId().equals(entity.getClientId()))
                    .findAny().ifPresent(object->{throw new RuntimeException("-");});
        }catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Transaction doesn't exist");
    }
}
