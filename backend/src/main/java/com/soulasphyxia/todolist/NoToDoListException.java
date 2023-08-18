package com.soulasphyxia.todolist;

public class NoToDoListException extends RuntimeException{
    public NoToDoListException(Long id) {
        super("No ToDo List with id " + id);
    }
}
