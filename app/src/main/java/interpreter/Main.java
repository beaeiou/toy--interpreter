package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select a program to run (1, 2, 3) or 0 to exit:");
            System.out.println("1. " + Examples.EX1);
            System.out.println("2. " + Examples.EX2);
            System.out.println("3. " + Examples.EX3);
            System.out.print("> ");

            String input = scanner.nextLine();
            if (input.equals("0")) break;

            IStmt selectedProgram = switch (input) {
                case "1" -> Examples.EX1;
                case "2" -> Examples.EX2;
                case "3" -> Examples.EX3;
                default -> null;
            };

            if (selectedProgram == null) {
                System.out.println("Invalid selection.");
                continue;
            }

            PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProgram);
            IRepository repo = new Repository(state);
            Controller controller = new Controller(repo, true);

            try {
                controller.allStep();
            } catch (Exception e) {
                System.out.println("Execution Error: " + e.getMessage());
            }
        }
    }
}