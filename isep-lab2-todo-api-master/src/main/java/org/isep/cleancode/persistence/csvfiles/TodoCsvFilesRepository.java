package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ITodoRepository;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoCsvFilesRepository implements ITodoRepository {

    private final Path csvPath;

    public TodoCsvFilesRepository() {
        String appData = System.getenv("APPDATA");
        Path dir = Paths.get(appData, "todo-app");
        this.csvPath = dir.resolve("todos.csv");

        try {
            Files.createDirectories(dir);
            if (!Files.exists(csvPath)) {
                Files.createFile(csvPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'initialiser le fichier CSV", e);
        }
    }

    @Override
    public void add(Todo todo) {
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardOpenOption.APPEND)) {
            writer.write(todo.getName() + "," + (todo.getDateEcheance() != null ? todo.getDateEcheance() : ""));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erreur d'écriture dans le fichier CSV", e);
        }
    }

    @Override
    public List<Todo> getAll() {
        List<Todo> todos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] champs = line.split(",", -1);
                Todo todo = new Todo(champs[0]);
                if (champs.length > 1 && !champs[1].isBlank()) {
                    todo.setDateEcheance(champs[1]);
                }
                todos.add(todo);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier CSV", e);
        }
        return todos;
    }

    @Override
    public boolean exists(String name) {
        return getAll().stream().anyMatch(t -> t.getName().equals(name));
    }
}
