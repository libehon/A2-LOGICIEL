package org.isep.cleancode.application;

import org.isep.cleancode.Todo;

import java.util.List;

public class TodoManager {

    private final ITodoRepository repository;

    public TodoManager(ITodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAllTodos() {
        return repository.getAll();
    }

    public boolean addTodo(Todo todo) {
        if (!todo.estNomValide()) {
            return false;
        }

        if (repository.exists(todo.getName())) {
            return false;
        }

        repository.add(todo);
        return true;
    }
}
