package org.isep.cleancode.calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {

    public double evaluerExpression(String expression) {
        Deque<Double> pile = new ArrayDeque<>();
        double nombre = 0;
        char operateur = '+';       //signe de l'opération
        boolean Positif = true;     //signe du nombre
        int index = 0;

        while (index< expression.length()) {
            char caractere = expression.charAt(index);

            if (Character.isWhitespace(caractere)) {
                index++;
                continue;
            }

            if (caractere == '+' || caractere == '-') {
                if (index == 0 || expression.charAt(index - 1) == '(' || estOperateur(expression.charAt(index - 1))) {
                    Positif = (caractere == '+') == Positif;
                } else {
                    appliquerOperation(pile, operateur, nombre);
                    operateur = caractere;
                    nombre = 0;
                    Positif = true;
                }
                index++;
                continue;
            }

            if (Character.isDigit(caractere) || caractere == '.') {
                StringBuilder sb = new StringBuilder();
                while (index < expression.length() &&
                        (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.')) {
                    sb.append(expression.charAt(index));
                    index++;
                }
                nombre = Double.parseDouble(sb.toString());
                if (!Positif) {
                    nombre = -nombre;
                }
                Positif = true;
                continue;
            }

            if (caractere == '(') {
                int fin = trouverFinParenthese(expression, index);
                String sousExpression = expression.substring(index + 1, fin);
                nombre = evaluerExpression(sousExpression);
                if (!Positif) {
                    nombre = -nombre;
                }
                Positif = true;
                index = fin + 1;
                continue;
            }

            if (estOperateur(caractere)) {
                appliquerOperation(pile, operateur, nombre);
                operateur = caractere;
                nombre = 0;
                Positif = true;
                index++;
                continue;
            }

            index++; // caractère non reconnu
        }

        appliquerOperation(pile, operateur, nombre);

        double resultat = 0;
        for (double n : pile) {
            resultat += n;
        }
        return resultat;
    }

    private boolean estOperateur(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private void appliquerOperation(Deque<Double> pile, char operateur, double nombre) {
        switch (operateur) {
            case '+':
                pile.push(nombre);
                break;
            case '-':
                pile.push(-nombre);
                break;
            case '*':
                pile.push(pile.pop() * nombre);
                break;
            case '/':
                pile.push(pile.pop() / nombre);
                break;
            default:
                throw new IllegalArgumentException("Opérateur inconnu : " + operateur);
        }
    }

    private int trouverFinParenthese(String expression, int indexOuverture) {
        int compteur = 0;
        for (int i = indexOuverture; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') compteur++;
            if (expression.charAt(i) == ')') compteur--;
            if (compteur == 0) return i;
        }
        throw new IllegalArgumentException("Parenthèse fermante manquante");
    }
}
