package ro.ubbcluj.maserver.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@Entity
public class Dog {
    @Id
    @GeneratedValue
    private Long ID;
    private String name;
    private String race;
    private String description;
    private int age;

}
