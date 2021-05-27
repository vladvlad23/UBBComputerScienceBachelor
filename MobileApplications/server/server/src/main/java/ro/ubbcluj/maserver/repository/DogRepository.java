package ro.ubbcluj.maserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubbcluj.maserver.model.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {

}
