package learning.java.todos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import learning.java.todos.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {}
