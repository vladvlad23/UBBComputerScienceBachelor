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
public class Client extends BaseEntity<Long>{

    private String clientName;
//
//    public Client(String clientName, Long aLong) {
//        super(aLong);
//        this.clientName = clientName;
//    }

    //
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Client client = (Client) o;
//        return Objects.equals(getClientName(), client.getClientName()) && Objects.equals(this.getId(), ((Client) o).getId());
//    }
}
