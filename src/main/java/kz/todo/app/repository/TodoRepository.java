package kz.todo.app.repository;

import kz.todo.app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    List<Todo> findByCompletedOrderByCreatedAtDesc(Boolean completed);
    
    List<Todo> findAllByOrderByCreatedAtDesc();
    
    @Query("SELECT t FROM Todo t WHERE LOWER(t.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY t.createdAt DESC")
    List<Todo> findByContentContainingIgnoreCaseOrderByCreatedAtDesc(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT t FROM Todo t WHERE t.completed = :completed AND LOWER(t.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY t.createdAt DESC")
    List<Todo> findByCompletedAndContentContainingIgnoreCaseOrderByCreatedAtDesc(
            @Param("completed") Boolean completed, 
            @Param("searchTerm") String searchTerm
    );
    
    long countByCompleted(Boolean completed);
}
