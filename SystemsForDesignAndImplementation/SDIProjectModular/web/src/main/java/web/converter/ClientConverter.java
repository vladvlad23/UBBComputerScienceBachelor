package web.converter;

import core.model.Client;
import org.springframework.stereotype.Component;
import web.dto.ClientDto;

/**
 * Created by radu.
 */
@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .clientName(dto.getClientName())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .clientName(client.getClientName())
                .build();

        dto.setId(client.getId());
        return dto;
    }
}
