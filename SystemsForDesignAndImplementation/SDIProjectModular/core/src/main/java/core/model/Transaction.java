package core.model;

import lombok.*;

import javax.persistence.Entity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Entity
public class Transaction extends BaseEntity<Long>{
    private Long bookId;
    private Long clientId;
    private int profit;
}
