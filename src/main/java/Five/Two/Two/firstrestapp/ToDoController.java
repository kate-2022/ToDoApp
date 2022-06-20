package Five.Two.Two.firstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ToDoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRespository userRepository;

    @GetMapping("/todo")
    public ResponseEntity<ToDo> get(@RequestParam (value = "id") int id ){

        // get todo from db by id
        Optional<ToDo> toDoInDb = todoRepository.findById(id);

        if(toDoInDb.isPresent()){
            return new ResponseEntity<ToDo>(toDoInDb.get(), HttpStatus.OK);
        }

        return new ResponseEntity("No todo found with id" +id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todo/all")
    public ResponseEntity<Iterable<ToDo>>getAll(@RequestHeader("api-secret") String secret) {
        // System.out.println(secret);

        var userBySecret = userRepository.findBySecret(secret);

        if (userBySecret.isPresent()) {
            Iterable<ToDo> allToTosInDb = todoRepository.findAllByUserId(userBySecret.get().getId());
            return new ResponseEntity<Iterable<ToDo>>(allToTosInDb, HttpStatus.OK);
        }
        return new ResponseEntity("Invalid API secret", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/todo")
    public ResponseEntity<ToDo> create(@RequestBody ToDo newToDo) {
        // save todo in db
        todoRepository.save(newToDo);
        return new ResponseEntity<ToDo>(newToDo, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/todo")
    public ResponseEntity delete(@RequestParam(value = "id")int id) {

        Optional<ToDo> toDoInDb = todoRepository.findById(id);

        if (toDoInDb.isPresent()) {
            todoRepository.deleteById(id);
            return new ResponseEntity("Todo deleted", HttpStatus.OK);
        }

        return new ResponseEntity("No todo to be deleted with id " + id +" existing.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity <ToDo> edit (@RequestBody ToDo editedToDo){
        Optional<ToDo> toDoInDb = todoRepository.findById(editedToDo.getId());

        if (toDoInDb.isPresent()) {
            //update
            ToDo savedToDo = todoRepository.save(editedToDo);
            return new ResponseEntity<ToDo>(savedToDo, HttpStatus.OK);
        }
        return new ResponseEntity("No todo to be updated with id " + editedToDo.getId() + " existing.", HttpStatus.NOT_FOUND);

    }

    @PatchMapping("/todo/setDone")
    public ResponseEntity<ToDo> setDone(@RequestParam(value="isDone") boolean isDone,
                                        @RequestParam(value= "id") int id){
        Optional<ToDo> toDoInDb = todoRepository.findById(id);

        if (toDoInDb.isPresent()) {
            //update isDone
            toDoInDb.get().setIsDone(isDone);
            ToDo savedToDo = todoRepository.save(toDoInDb.get());
            return new ResponseEntity<ToDo>(savedToDo, HttpStatus.OK);
        }

        return new ResponseEntity("No todo to be updated with id " + id, HttpStatus.NOT_FOUND);


    }
}

