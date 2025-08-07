package kz.todo.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTodoRequest {
    
    @Size(max = 500, message = "Todo content cannot exceed 500 characters")
    private String content;
    
    private Boolean completed;
}
