package learning.java.todos.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import learning.java.todos.entities.Todo;
import learning.java.todos.repositories.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class TodoController {
    private final TodoRepository todos;

    public TodoController(TodoRepository todos) {
        this.todos = todos;
    }

    @GetMapping
    public List<Todo> index() {
        return todos.findAll(Sort.by("id"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return todos.findById(id)
            .<ResponseEntity<?>>map(todo -> ResponseEntity.ok(todo))
            .orElseGet(TodoController::notFound);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody Todo newTodo) {
        return todos.save(newTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Todo fixTodo) {
        return todos.findById(id).<ResponseEntity<?>>map(todo -> {
            todo.setSubject(fixTodo.getSubject());
            todo.setDetails(fixTodo.getDetails());
            return ResponseEntity.ok(todos.save(todo));
        }).orElseGet(TodoController::notFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        if (!todos.existsById(id)) return notFound();
        todos.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    static ResponseEntity<Map<String, String>> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "not found"));
    }
}
