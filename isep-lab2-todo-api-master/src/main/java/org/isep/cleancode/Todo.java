package org.isep.cleancode;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Todo {

    private String name;

    // Nouveau champ : date d'échéance (facultative)
    private LocalDate dateEcheance;

    public Todo(String name) {
        this.name = name;
    }

    // Getter/Setter existants
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Nouveau getter/setter
    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheanceStr) {
        if (dateEcheanceStr == null || dateEcheanceStr.isBlank()) {
            this.dateEcheance = null;
            return;
        }

        try {
            this.dateEcheance = LocalDate.parse(dateEcheanceStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide (attendu : AAAA-MM-JJ)");
        }
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
