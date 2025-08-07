package kz.todo.app.config;

import kz.todo.app.entity.Todo;
import kz.todo.app.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final TodoRepository todoRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (todoRepository.count() == 0) {
            log.info("Initializing sample todo data...");
            
            Todo todo1 = Todo.builder()
                    .content("Complete project documentation")
                    .completed(false)
                    .build();
            
            Todo todo2 = Todo.builder()
                    .content("Review code changes")
                    .completed(true)
                    .build();
            
            Todo todo3 = Todo.builder()
                    .content("Deploy to production")
                    .completed(false)
                    .build();
            
            todoRepository.save(todo1);
            todoRepository.save(todo2);
            todoRepository.save(todo3);
            
            log.info("Sample data initialized successfully");
        }
    }
}
