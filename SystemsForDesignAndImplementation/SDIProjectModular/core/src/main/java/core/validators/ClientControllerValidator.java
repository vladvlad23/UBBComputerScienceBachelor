package core.validators;
import core.exceptions.ValidatorException;
import core.model.BaseEntity;
import core.model.Client;
import core.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class ClientControllerValidator{
    @Autowired
    ClientRepository repository;

    public void validateNonExistence(Client entity) throws ValidatorException {
        Iterable<? extends BaseEntity<Long>> iterable = repository.findAll();

        StreamSupport.stream(iterable.spliterator(), false)
                .filter(object->((Client)object).getClientName().equals(entity.getClientName()))
                .findAny().ifPresent(object->{throw new ValidatorException("Validation Failure: Client already exists.");});
    }

    public void validateExistence(Client entity) throws ValidatorException{
        try {
            Iterable<? extends BaseEntity<Long>> iterable = repository.findAll();

            StreamSupport.stream(iterable.spliterator(), false)
                    .filter(object->((Client)object).getClientName().equals(entity.getClientName()))
                    .findAny().ifPresent(object->{throw new RuntimeException("-");});
        }catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Client doesn't exist");
    }
}
