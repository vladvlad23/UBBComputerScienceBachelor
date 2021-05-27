package core.validators.domain_validators;


import core.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDomainValidator implements Validator<Transaction> {
    @Override
    public void validate(Transaction entity) throws ValidatorException {
    }
}
