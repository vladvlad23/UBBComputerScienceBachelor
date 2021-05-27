package core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    private ID id;

//    public BaseEntity(ID id) {
//        this.id = id;
//    }
}
