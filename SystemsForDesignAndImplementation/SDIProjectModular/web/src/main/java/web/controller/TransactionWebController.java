package web.controller;

import core.controller.interfaces.TransactionServiceInterface;
import core.exceptions.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.TransactionConverter;
import web.dto.TransactionDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionWebController {
    public static final Logger log= LoggerFactory.getLogger(TransactionWebController.class);

    @Autowired
    private TransactionServiceInterface transactionController;

    @Autowired
    private TransactionConverter transactionConverter;


    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    List<TransactionDto> getTransactions() {
        log.trace("getTransactions: method entered");
        log.trace("getTransactions: method exit");
        return new ArrayList<>(transactionConverter
                .convertModelsToDtos(transactionController.getAll()));

    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    TransactionDto saveTransaction(@RequestBody TransactionDto transactionDto) throws ControllerException {
        log.trace("saveTransaction: method entered: transactionDto = {}",transactionDto);

        TransactionDto returnValue = transactionConverter.convertModelToDto(
                transactionController.add(transactionConverter.convertDtoToModel(transactionDto)));

        log.trace("saveTransaction: method exit: returnValue={}",returnValue);
        return returnValue;
    }

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.PUT)
    TransactionDto updateTransaction(@PathVariable Long id,
                           @RequestBody TransactionDto transactionDto) {

        log.trace("updateTransaction: method entered: transactionDto={}",transactionDto);

        TransactionDto returnValue = transactionConverter.convertModelToDto(
                transactionController.update(transactionConverter.convertDtoToModel(transactionDto)));

        log.trace("updateTransaction: method exit: returnValue={}",returnValue);

        return returnValue;
    }

    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteTransaction(@PathVariable Long id) throws ControllerException {

        log.trace("deleteTransaction: entered method: id={}",id);

        transactionController.remove(id);

        log.trace("deleteTransaction: exit method");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
