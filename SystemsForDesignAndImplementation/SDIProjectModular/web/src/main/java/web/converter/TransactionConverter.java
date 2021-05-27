package web.converter;

import core.model.Transaction;
import org.springframework.stereotype.Component;
import web.dto.TransactionDto;

/**
 * Created by radu.
 */
@Component
public class TransactionConverter extends BaseConverter<Transaction, TransactionDto> {
    @Override
    public Transaction convertDtoToModel(TransactionDto dto) {
        Transaction transaction = Transaction.builder()
                .bookId(dto.getBookId())
                .clientId(dto.getClientId())
                .profit(dto.getProfit())
                .build();
        transaction.setId(dto.getId());
        return transaction;
    }

    @Override
    public TransactionDto convertModelToDto(Transaction transaction) {
        TransactionDto dto = TransactionDto.builder()
                .bookId(transaction.getBookId())
                .clientId(transaction.getClientId())
                .profit(transaction.getProfit())
                .build();

        dto.setId(transaction.getId());
        return dto;
    }
}

