package web.controller;

import core.controller.interfaces.ClientServiceInterface;
import core.exceptions.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.ClientConverter;
import web.dto.ClientDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientWebController {
    public static final Logger log= LoggerFactory.getLogger(ClientWebController.class);

    @Autowired
    private ClientServiceInterface clientService;

    @Autowired
    private ClientConverter clientConverter;


    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    List<ClientDto> getClients() {
        log.trace("getClients: method entered");
        log.trace("getClients: method exit");
        return new ArrayList<>(clientConverter
                .convertModelsToDtos(clientService.getAll()));

    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) throws ControllerException {
        log.trace("saveClient: method entered: clientDto = {}",clientDto);

        ClientDto returnValue = clientConverter.convertModelToDto(
                clientService.add(clientConverter.convertDtoToModel(clientDto)));

        log.trace("saveClient: method exit: returnValue={}",returnValue);
        return returnValue;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,
                       @RequestBody ClientDto clientDto) {

        log.trace("updateClient: method entered: clientDto={}",clientDto);

        ClientDto returnValue = clientConverter.convertModelToDto(
                clientService.update(clientConverter.convertDtoToModel(clientDto)));

        log.trace("updateClient: method exit: returnValue={}",returnValue);

        return returnValue;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) throws ControllerException {

        log.trace("deleteClient: entered method: id={}",id);

        clientService.remove(id);

        log.trace("deleteClient: exit method");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
