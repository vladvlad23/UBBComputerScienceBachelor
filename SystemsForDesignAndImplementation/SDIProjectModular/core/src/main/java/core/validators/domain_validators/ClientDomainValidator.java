package core.validators.domain_validators;

import core.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientDomainValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        if (entity.getClientName().equals(""))
            throw new ValidatorException("Validation Failure: Invalid client name");

    }
}
