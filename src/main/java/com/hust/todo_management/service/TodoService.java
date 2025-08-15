package com.hust.todo_management.service;

import com.hust.todo_management.dto.TodoDto;
import com.hust.todo_management.entity.Todo;
import com.hust.todo_management.exception.ResourceNotFoundException;
import com.hust.todo_management.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    public TodoDto addTodo(TodoDto todoDto){
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo existTodo = todoRepository.save(todo);
        TodoDto todoDto1 = modelMapper.map(existTodo, TodoDto.class);
        return todoDto1;
    }

    public TodoDto getTodoById(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id ));
        return modelMapper.map(todo, TodoDto.class);
    }

    public List<TodoDto> getAllTodos(){
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    public TodoDto updateTodo(TodoDto todoDto, Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }

    public void deleteTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todoRepository.deleteById(id);
    }

    public TodoDto completeTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(true);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }

    public TodoDto inCompleteTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(false);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }
}
