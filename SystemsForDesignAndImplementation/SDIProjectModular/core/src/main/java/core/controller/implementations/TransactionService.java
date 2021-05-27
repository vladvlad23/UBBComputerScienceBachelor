package core.controller.implementations;


import core.controller.interfaces.TransactionServiceInterface;
import core.exceptions.ControllerException;
import core.model.BaseEntity;
import core.model.Book;
import core.model.Client;
import core.model.Transaction;
import core.repository.TransactionRepository;
import core.validators.TransactionControllerValidator;
import core.validators.domain_validators.TransactionDomainValidator;
import core.validators.id_validators.TransactionIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionService implements TransactionServiceInterface {

    public static final Logger log = LoggerFactory.getLogger(TransactionService.class);


    @Autowired
    private TransactionRepository repository;

    @Autowired
    private ClientService clientController;

    @Autowired
    private BookService bookController;

    @Autowired
    private TransactionIdValidator idValidator;

    @Autowired
    private TransactionDomainValidator transactionDomainValidator;

    @Autowired
    private TransactionControllerValidator transactionControllerValidator;


    @Override
    public List<Transaction> getAll() {
        log.trace("getAll - method entered:");

        Iterable<Transaction> iterable = repository.findAll();

        log.trace("getAll - method exit:");

        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public void remove(Long transactionId){
        log.trace("remove - method entered: transactionId={}",transactionId);

        Transaction newTransaction = new Transaction(0L,0L,0);
        newTransaction.setId(transactionId);

        idValidator.validateExistence(newTransaction);
        repository.delete(repository.findById(transactionId).get());

        log.trace("remove - method exit:");

    }

    @Override
    public BaseEntity<Long> create(String data) throws ControllerException {

        log.trace("create - method entered: data={}",data);

        String[] dataString = data.split(" ");

        Long id;
        Integer profit;

        try{
            profit = new Integer(dataString[2]);
            id = new Long(dataString[3]);
        }
        catch (NumberFormatException e){ throw new ControllerException("No id or profit found");}
        catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }

        Transaction returnTransaction = new Transaction(
                Long.parseLong(dataString[0]),
                Long.parseLong(dataString[1]),
                profit);

        returnTransaction.setId(id);

        log.trace("create - method entered: transaction={}",returnTransaction);
        return returnTransaction;
    }


    @Override
    public Transaction add(Transaction addedTransaction){
        log.trace("add - method entered: transaction={}",addedTransaction);


        idValidator.validateNonExistence(addedTransaction);
        transactionDomainValidator.validate(addedTransaction);
        transactionControllerValidator.validateBookExistence(addedTransaction);
        transactionControllerValidator.validateClientExistence(addedTransaction);

        repository.save(addedTransaction);
        log.trace("add - method exit: addedTransaction={}",addedTransaction);
        return addedTransaction;

    }

    @Override
    public Transaction update(Transaction newTransaction){
        log.trace("update - method entered: newTransaction={}",newTransaction);

        idValidator.validateExistence(newTransaction);
        repository.save(newTransaction);

        log.trace("update - method exit: newTransaction={}",newTransaction);
        return newTransaction;
    }


//    @Override
//    public Iterable<Transaction> findAll(Sort sort) {
//        log.trace("findAll - method entered:");
//
//        List<Transaction> returnList  = repository.findAll();
//        returnList.sort(sort.getSortComparator());
//
//        log.trace("findAll - method exit:");
//
//        return returnList;
//    }


    @Override
    public List<Transaction> filter(Function<BaseEntity<Long>, Boolean> lambda) {
        log.trace("create - method entered:");
        log.trace("create - method exit:");
        return repository.findAll().stream().filter(lambda::apply).collect(Collectors.toList());
    }

    public void cascadeRemoveBook(Long bookId) throws ControllerException {
        log.trace("cascadeRemoveBook - method entered: bookId",bookId);
        Optional<Book> optionalBook = bookController.getAll().stream().filter(e -> e.getId().equals(bookId)).findFirst();
        if(optionalBook.isPresent()){
            Book resultedBook = optionalBook.get();
            this.getAll().stream()
                    .filter(e -> e.getBookId().equals(resultedBook.getId()))
                    .forEach(e-> repository.delete(e));
        }
        else{
            throw new ControllerException("No book by that id. What are you trying to delete?");
        }
        log.trace("cascadeRemoveBook - method exit:");

    }

    public void cascadeRemoveClient(Long clientId) throws ControllerException {
        log.trace("cascadeRemoveClient - method entered: clientId",clientId);

        Optional<Client> optionalClient = clientController.getAll().stream().filter(e -> e.getId().equals(clientId)).findFirst();
        if(optionalClient.isPresent()){
            Client resultedClient = optionalClient.get();
            this.getAll().stream()
                    .filter(e -> e.getClientId().equals(resultedClient.getId()))
                    .forEach(e-> repository.delete(e));
        }
        else{
            throw new ControllerException("No client by that id. What are you trying to delete?");
        }
        log.trace("cascadeRemoveClient - method exit:");
    }
}

