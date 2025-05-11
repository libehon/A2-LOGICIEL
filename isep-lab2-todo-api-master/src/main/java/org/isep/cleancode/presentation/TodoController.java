package org.isep.cleancode.presentation;

import com.google.gson.Gson;
import org.isep.cleancode.Todo;
import org.isep.cleancode.application.TodoManager;
import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.persistence.csvfiles.TodoCsvFilesRepository;
import spark.Request;
import spark.Response;

public class TodoController {

    private static final Gson gson = new Gson();
    private final TodoManager todoManager;

    public TodoController(ITodoRepository repository) {
        this.todoManager = new TodoManager(repository);
    }

    public Object getAllTodos(Request request, Response response) {
        response.type("application/json");
        return gson.toJson(todoManager.getAllTodos());
    }

    public Object createTodo(Request request, Response response) {
        try {
            Todo newTodo = gson.fromJson(request.body(), Todo.class);
            String dueDate = request.queryParams("dueDate");
            newTodo.setDateEcheance(dueDate);

            boolean success = todoManager.addTodo(newTodo);

            if (!success) {
                response.status(400);
                return "Error: Invalid or duplicate name.";
            }

            response.status(201);
            return gson.toJson(newTodo);

        } catch (Exception e) {
            response.status(400);
            return "Error: Invalid request.";
        }
    }
}
