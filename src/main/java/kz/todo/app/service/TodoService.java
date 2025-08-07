package kz.todo.app.service;

import kz.todo.app.dto.CreateTodoRequest;
import kz.todo.app.dto.TodoDto;
import kz.todo.app.dto.UpdateTodoRequest;
import kz.todo.app.entity.Todo;
import kz.todo.app.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    public List<TodoDto> getAllTodos(String filter, String search) {
        List<Todo> todos;
        
        if (search != null && !search.trim().isEmpty()) {
            if ("active".equals(filter)) {
                todos = todoRepository.findByCompletedAndContentContainingIgnoreCaseOrderByCreatedAtDesc(false, search.trim());
            } else if ("completed".equals(filter)) {
                todos = todoRepository.findByCompletedAndContentContainingIgnoreCaseOrderByCreatedAtDesc(true, search.trim());
            } else {
                todos = todoRepository.findByContentContainingIgnoreCaseOrderByCreatedAtDesc(search.trim());
            }
        } else {
            if ("active".equals(filter)) {
                todos = todoRepository.findByCompletedOrderByCreatedAtDesc(false);
            } else if ("completed".equals(filter)) {
                todos = todoRepository.findByCompletedOrderByCreatedAtDesc(true);
            } else {
                todos = todoRepository.findAllByOrderByCreatedAtDesc();
            }
        }
        
        return todos.stream()
                .map(this::mapToDto)
                .toList();
    }
    
    public TodoDto createTodo(CreateTodoRequest request) {
        Todo todo = Todo.builder()
                .content(request.getContent().trim())
                .completed(false)
                .build();
        
        Todo savedTodo = todoRepository.save(todo);
        log.info("Created new todo with id: {}", savedTodo.getId());
        
        return mapToDto(savedTodo);
    }
    
    public Optional<TodoDto> getTodoById(Long id) {
        return todoRepository.findById(id)
                .map(this::mapToDto);
    }
    
    public Optional<TodoDto> updateTodo(Long id, UpdateTodoRequest request) {
        return todoRepository.findById(id)
                .map(todo -> {
                    if (request.getContent() != null && !request.getContent().trim().isEmpty()) {
                        todo.setContent(request.getContent().trim());
                    }
                    if (request.getCompleted() != null) {
                        todo.setCompleted(request.getCompleted());
                    }
                    Todo updatedTodo = todoRepository.save(todo);
                    log.info("Updated todo with id: {}", updatedTodo.getId());
                    return mapToDto(updatedTodo);
                });
    }
    
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            log.info("Deleted todo with id: {}", id);
            return true;
        }
        return false;
    }
    
    public long getTotalCount() {
        return todoRepository.count();
    }
    
    public long getActiveCount() {
        return todoRepository.countByCompleted(false);
    }
    
    public long getCompletedCount() {
        return todoRepository.countByCompleted(true);
    }
    
    private TodoDto mapToDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .completed(todo.getCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
