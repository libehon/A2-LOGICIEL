 package org.isep.cleancode;

import org.isep.cleancode.calculator.Calculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Entrez votre opération (Entrez 'exit' pour sortir) :");

        while(true) {
            System.out.print("> ");
            String saisie = scanner.nextLine().trim();

            if (saisie.equalsIgnoreCase("exit")) {
                System.out.println("Bonne journée !");
                break;
            }

            if (!saisie.isEmpty()) {
                try {
                    double resultat = calculator.evaluerExpression(saisie);
                    System.out.println("= " + resultat);
                } catch (Exception e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }
        }
        scanner.close();
    }
}
