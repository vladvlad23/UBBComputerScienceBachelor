package core.validators.domain_validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
