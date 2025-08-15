package com.hust.todo_management.service;

import com.hust.todo_management.dto.TodoDto;
import com.hust.todo_management.entity.Todo;
import com.hust.todo_management.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
