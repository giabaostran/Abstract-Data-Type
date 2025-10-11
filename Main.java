import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Scanner kb = new Scanner(System.in); // create only once

        while (true) {
            System.out.println("\n===== ADT MENU =====");
            System.out.println("1. ArrayStack");
            System.out.println("2. ArrayQueue");
            System.out.println("3. ArrayDeque");
            System.out.println("4. DualArrayStack");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(kb.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> ArrayStack.runArrayStack(kb, new ArrayStack());
                case 2 -> ArrayQueue.runArrayQueue(kb, new ArrayQueue());
                case 3 -> ArrayDeque.runArrayDeque(kb, new ArrayDeque());
                case 4 -> DualArrayStack.runDualArrayStack(kb, new DualArrayStack());
                case 0 -> {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid option. Please enter 0-3.");
            }
        }
    }
}
