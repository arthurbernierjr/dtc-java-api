package learning.java.todos.controllers;

import java.util.List;
import learning.java.todos.entities.Todo;
import learning.java.todos.repositories.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {
    private final TodoRepository todos;

    public TodoController(TodoRepository todos) {
        this.todos = todos;
    }

    @GetMapping
    public List<Todo> index() {
        return todos.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> show(@PathVariable Long id) {
        return ResponseEntity.of(todos.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody Todo newTodo) {
        return todos.save(newTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo fixTodo) {
        return todos.findById(id).map(todo -> {
            todo.setSubject(fixTodo.getSubject());
            todo.setDetails(fixTodo.getDetails());
            return ResponseEntity.ok(todos.save(todo));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        if (!todos.existsById(id)) return ResponseEntity.notFound().build();
        todos.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
