package org.isep.cleancode.application;

import org.isep.cleancode.Todo;

import java.util.List;

public interface ITodoRepository {
    void add(Todo todo);
    List<Todo> getAll();
    boolean exists(String name);
}

