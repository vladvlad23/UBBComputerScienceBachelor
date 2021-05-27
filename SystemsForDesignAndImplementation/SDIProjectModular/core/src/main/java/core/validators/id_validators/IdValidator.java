package core.validators.id_validators;


import core.model.BaseEntity;
import core.repository.Repository;
import core.validators.domain_validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public abstract class IdValidator<T extends BaseEntity<Long>>{
    @Autowired
    Repository<T,Long> repo;

    public void validateNonExistence(BaseEntity<Long> entity) throws ValidatorException {
        Iterable<T> iterable = repo.findAll();

        StreamSupport.stream(iterable.spliterator(), false)
                .map(BaseEntity::getId)
                .filter(item -> item.equals(entity.getId()))
                .findAny()
                .ifPresent(item -> {
                    throw new ValidatorException("Validation Failure: Already existent id.");
                });
    }

    public void validateNonExistence(Long entityId) throws ValidatorException {
        Iterable<T> iterable = repo.findAll();

        StreamSupport.stream(iterable.spliterator(), false)
                .map(BaseEntity::getId)
                .filter(item -> item.equals(entityId))
                .findAny()
                .ifPresent(item -> {
                    throw new ValidatorException("Validation Failure: Already existent id.");
                });
    }

    public void validateExistence(BaseEntity<Long> entity) throws ValidatorException {
        Iterable<T> iterable = repo.findAll();
        try {
            StreamSupport.stream(iterable.spliterator(), false)
                    .map(BaseEntity::getId)
                    .filter(item -> item.equals(entity.getId()))
                    .findAny()
                    .ifPresent(item -> {
                        throw new RuntimeException("-");
                    });
        }
        catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Inexistent id.");
    }

    public void validateExistence(Long entityId) throws ValidatorException {
        Iterable<T> iterable = repo.findAll();
        try {
            StreamSupport.stream(iterable.spliterator(), false)
                    .map(BaseEntity::getId)
                    .filter(item -> item.equals(entityId))
                    .findAny()
                    .ifPresent(item -> {
                        throw new RuntimeException("-");
                    });
        }
        catch (RuntimeException e){return;}
        throw new ValidatorException("Validation Failure: Inexistent id.");
    }
}
