package Five.Two.Two.firstrestapp;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRespository extends CrudRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findBySecret (String secret);
}
