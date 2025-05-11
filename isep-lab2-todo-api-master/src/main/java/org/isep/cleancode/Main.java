package org.isep.cleancode;

import static spark.Spark.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.isep.cleancode.application.ITodoRepository;
import org.isep.cleancode.persistence.csvfiles.TodoCsvFilesRepository;
import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import org.isep.cleancode.presentation.TodoController;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        port(4567);

        Properties config = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                config.load(input);
            } else {
                System.err.println("Fichier config.properties introuvable. Mode CSV par défaut.");
                config.setProperty("repository", "CSV");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier config.properties", e);
        }

        String mode = config.getProperty("repository", "CSV").toUpperCase();
        ITodoRepository repository;

        switch (mode) {
            case "INMEMORY":
                repository = new TodoInMemoryRepository();
                System.out.println("Mode : In-Memory Repository");
                break;
            case "CSV":
            default:
                repository = new TodoCsvFilesRepository();
                System.out.println("Mode : CSV Repository");
                break;
        }

        TodoController controller = new TodoController(repository);

        get("/todos", controller::getAllTodos);
        post("/todos", controller::createTodo);
    }
}

