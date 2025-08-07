package kz.todo.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequest {
    
    @NotBlank(message = "Todo content cannot be empty")
    @Size(max = 500, message = "Todo content cannot exceed 500 characters")
    private String content;
}
