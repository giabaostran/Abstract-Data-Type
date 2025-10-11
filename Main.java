import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // You can switch which structure to test by calling:
        // runArrayStackMenu();
        // runQueueMenu();
        runArrayDequeMenu();
    }

    

    

    // ==================== ARRAY DEQUE ====================
    public static void runArrayDequeMenu() {
        Scanner sc = new Scanner(System.in);
        ArrayDeque deque = new ArrayDeque();

        while (true) {
            System.out.println("\n===== ARRAY DEQUE MENU =====");
            System.out.println("1. Add First");
            System.out.println("2. Add Last");
            System.out.println("3. Remove First");
            System.out.println("4. Remove Last");
            System.out.println("5. Print Deque (horizontal)");
            System.out.println("6. Get element by index");
            System.out.println("7. Clear Deque");
            System.out.println("8. Check if Empty");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter element to add first: ");
                    Object data = sc.nextLine();
                    deque.addFirst(data);
                    System.out.println("Added to front: " + data);
                }
                case 2 -> {
                    System.out.print("Enter element to add last: ");
                    Object data = sc.nextLine();
                    deque.addLast(data);
                    System.out.println("Added to back: " + data);
                }
                case 3 -> {
                    if (deque.isEmpty())
                        System.out.println("Deque is empty.");
                    else {
                        Object removed = deque.get(0);
                        deque.removeFirst();
                        System.out.println("Removed first: " + removed);
                    }
                }
                case 4 -> {
                    if (deque.isEmpty())
                        System.out.println("Deque is empty.");
                    else {
                        Object removed = deque.get(deque.size() - 1);
                        deque.removeLast();
                        System.out.println("Removed last: " + removed);
                    }
                }
                case 5 -> {
                    if (deque.isEmpty()) {
                        System.out.println("Deque is empty.");
                    } else {
                        System.out.print("Deque (front → back): ");
                        for (int i = 0; i < deque.size(); i++) {
                            System.out.print("[" + deque.get(i) + "]");
                            if (i < deque.size() - 1)
                                System.out.print(" ");
                        }
                        System.out.println("  <-- horizontal view");
                    }
                    System.out.println("Size: " + deque.size());
                }
                case 6 -> {
                    if (deque.isEmpty()) {
                        System.out.println("Deque is empty.");
                    } else {
                        System.out.print("Enter index (0–" + (deque.size() - 1) + "): ");
                        int idx = Integer.parseInt(sc.nextLine());
                        if (idx < 0 || idx >= deque.size())
                            System.out.println("Invalid index.");
                        else
                            System.out.println("Element at index " + idx + ": " + deque.get(idx));
                    }
                }
                case 7 -> {
                    deque.clear();
                    System.out.println("Deque cleared.");
                }
                case 8 -> System.out.println(deque.isEmpty() ? "Deque is empty." : "Deque is not empty.");
                case 0 -> {
                    System.out.println("Exiting ArrayDeque test...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
