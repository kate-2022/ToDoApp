package Five.Two.Two.firstrestapp;

import org.springframework.data.repository.CrudRepository;
import java.util.Set;

public interface TodoRepository extends CrudRepository<ToDo, Integer> {

    Set<ToDo> findAllByUserId(int userId);
}
