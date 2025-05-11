package org.isep.cleancode.persistence.inmemory;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.util.ArrayList;
import java.util.List;

public class TodoInMemoryRepository implements ITodoRepository {

    private final List<Todo> todos = new ArrayList<>();

    @Override
    public void add(Todo todo) {
        todos.add(todo);
    }

    @Override
    public List<Todo> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public boolean exists(String name) {
        return todos.stream().anyMatch(todo -> todo.getName().equals(name));
    }
}
