package core.controller.implementations;

import core.controller.interfaces.ClientServiceInterface;
import core.exceptions.ControllerException;
import core.model.BaseEntity;
import core.model.Client;
import core.repository.ClientRepository;
import core.validators.ClientControllerValidator;
import core.validators.domain_validators.ClientDomainValidator;
import core.validators.id_validators.ClientIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService implements ClientServiceInterface {

    public static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ClientControllerValidator clientControllerValidator;

    @Autowired
    private ClientDomainValidator clientDomainValidator;

    @Autowired
    private ClientIdValidator idValidator;

    @Autowired
    private TransactionService transactionService;

    @Override
    public List<Client> getAll() {
        log.trace("getAll - method entered:");
        Iterable<Client> iterable = repository.findAll();
        log.trace("getAll - method exit:");

        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Client create(String data) throws ControllerException {
        log.trace("create - method entered: data={}",data);

        String[] dataString = data.split(" ");
        Long id;

        try{ id = new Long(dataString[1]); }
        catch (NumberFormatException e){ throw new ControllerException("No id found"); }
        catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }


        Client returnClient = new Client(dataString[0]);
        returnClient.setId(id);

        log.trace("create - method exit: client={}",returnClient);

        return returnClient;
    }

    @Override
    public Client add(Client addedItem){
        log.trace("add - method entered:item={}",addedItem);

        idValidator.validateNonExistence(addedItem);
        clientDomainValidator.validate(addedItem);
        clientControllerValidator.validateNonExistence(addedItem);


        repository.save(addedItem);
        log.trace("add - method exit: addedClient= {}",addedItem);
        return addedItem;
    }

    @Override
    public void remove(Long ClientID){
        log.trace("remove - method entered: clientId={}",ClientID);

        Client newClient = new Client("-");
        newClient.setId(ClientID);

        idValidator.validateExistence(newClient);
        transactionService.getAll().stream()
                .filter(transaction -> transaction.getClientId().equals(ClientID))
                .forEach(transaction -> {
                    transactionService.remove(transaction.getId());
                });
        repository.delete(repository.findById(ClientID).get());

        log.trace("remove - method exit:");

    }

    @Override
    public Client update(Client newClient){
        log.trace("update - method entered: client={}",newClient);


        idValidator.validateExistence(newClient);
        repository.save(newClient);

        log.trace("update - method exit: newClient={}",newClient);
        return newClient;

    }
//
//    @Override
//    public Iterable<Client> findAll(Sort sort) {
//        log.trace("findAll - method entered:");
//
//        List<Client> returnList  = repository.findAll();
//        returnList.sort(sort.getSortComparator());
//
//        log.trace("findAll - method exit:");
//
//        return returnList;
//    }


    @Override
    public List<Client> filter(Function<BaseEntity<Long>, Boolean> lambda) {
        log.trace("filter - method entered:");
        log.trace("filter - method exit:");

        return repository.findAll().stream().filter(lambda::apply).collect(Collectors.toList());
    }

}
