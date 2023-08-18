package com.soulasphyxia.todolist;


import com.soulasphyxia.todolist.model.ToDoList;
import com.soulasphyxia.todolist.repositories.TaskRepository;
import com.soulasphyxia.todolist.repositories.ToDoListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToDoListService {
    private final ToDoListRepository toDoListRepository;
    private final TaskRepository taskRepository;

    public List<ToDoList> allLists() {
        return toDoListRepository.findAll();
    }


    public ToDoList singleList(Long id) {
        Optional<ToDoList> toDoListOptional = toDoListRepository.findById(id);
        if(toDoListOptional.isPresent()) {
            return toDoListOptional.get();
        }else {
            throw new NoToDoListException(id);
        }
    }


    public ToDoList add(ToDoList list) {

        toDoListRepository.save(list);
        if(!list.getTasks().isEmpty()) {
            taskRepository.saveAll(list.getTasks());
        }
        return list;
    }

    public ToDoList delete(Long id) {
        Optional<ToDoList> toDoListOptional = toDoListRepository.findById(id);
        if(toDoListOptional.isPresent()) {
            toDoListRepository.deleteById(id);
            return toDoListOptional.get();
        }else {
            throw new NoToDoListException(id);
        }
    }


    public ToDoList update(ToDoList list) {
        Optional<ToDoList> toDoListOptional = toDoListRepository.findById(list.getId());
        if(toDoListOptional.isPresent()){
            toDoListRepository.save(list);
            return list;
        }else{
            throw new NoToDoListException(list.getId());
        }

    }


}
