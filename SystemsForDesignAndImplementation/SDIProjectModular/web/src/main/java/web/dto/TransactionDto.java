package web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class TransactionDto extends BaseDto {
    private Long bookId;
    private Long clientId;
    private int profit;

}
