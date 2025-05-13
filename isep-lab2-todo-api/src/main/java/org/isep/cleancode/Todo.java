package org.isep.cleancode;

public class Todo {

    private String name;
    private String dueDate; // On utilise String pour simplifier la désérialisation JSON

    public Todo(String name) {
        this.name = name;
    }

    // Constructeur sans argument (important pour Gson)
    public Todo() {}

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // Règles métier
    public boolean estNomValide() {
        return name != null && !name.isBlank() && name.length() < 64;
    }

    @Override
    public boolean equals(Object objet) {
        if (this == objet) return true;
        if (!(objet instanceof Todo)) return false;
        Todo autre = (Todo) objet;
        return name.equals(autre.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
