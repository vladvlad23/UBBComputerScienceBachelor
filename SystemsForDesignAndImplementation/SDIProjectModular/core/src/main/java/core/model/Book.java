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
public class Book extends BaseEntity<Long>{
    private String bookName;
    private int bookPrice;

}
