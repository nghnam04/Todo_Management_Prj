package com.hust.todo_management.controller;

import com.hust.todo_management.dto.TodoDto;
import com.hust.todo_management.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto dto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id){
        TodoDto todoDto = todoService.getTodoById(id);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable Long id){
        TodoDto dto = todoService.updateTodo(todoDto, id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Delete Todo Successfully!");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("/complete/{id}")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id){
        TodoDto todoDto = todoService.completeTodo(id);
        return ResponseEntity.ok(todoDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("/incomplete/{id}")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id){
        TodoDto todoDto = todoService.inCompleteTodo(id);
        return ResponseEntity.ok(todoDto);
    }
}
