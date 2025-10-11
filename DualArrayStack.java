import java.util.Scanner;

/**
 * DualArrayStack is a data structure using two ArrayStacks (front and back)
 * to simulate a deque-like structure that allows efficient access and
 * modification
 * at both ends. Supports random access, add, remove, get, and set operations.
 */
public class DualArrayStack {

    private ArrayStack front, back;
    private int n;

    /** Constructor: Initializes empty front and back stacks */
    public DualArrayStack() {
        front = new ArrayStack();
        back = new ArrayStack();
        n = 0;
    }

    /**
     * Retrieves element at given index.
     * 
     * @param index position of element (0-based)
     * @return the element at the specified index
     */
    public Object get(int index) {
        if (index < front.size()) {
            return front.get(front.size() - index - 1); // front stack is reversed
        } else {
            return back.get(index - front.size());
        }
    }

    /**
     * Updates element at given index.
     * 
     * @param index position of element
     * @param data  new value to set
     */
    public void set(int index, Object data) {
        if (index < front.size()) {
            front.set(front.size() - index - 1, data);
        } else {
            back.set(index - front.size(), data);
        }
    }

    /**
     * Inserts an element at a specific index.
     * 
     * @param index position to insert at
     * @param data  value to insert
     */
    public void add(int index, Object data) {
        if (index <= front.size()) {
            front.add(front.size() - index, data);
        } else {
            back.add(index - front.size(), data);
        }
        balance();
        n++;
    }

    /** Adds an element at the front */
    public void addFirst(Object data) {
        add(0, data);
    }

    /** Adds an element at the back */
    public void addLast(Object data) {
        add(n, data);
    }

    /**
     * Removes element at specific index
     * 
     * @param index position to remove
     */
    public Object remove(int index) {
        Object dummy;
        if (index < front.size()) {
            dummy = front.remove(front.size() - index - 1);
        } else {
            dummy = back.remove(index - front.size());
        }
        n--;
        balance();
        return dummy;
    }

    /** Removes the first element */
    public Object removeFirst() {
        return remove(0);
    }

    /** Removes the last element */
    public Object removeLast() {
        return remove(n - 1);
    }

    /** Returns the current size */
    public int size() {
        return n;
    }

    /** Balances the front and back stacks (currently a placeholder) */
    private void balance() {
        // Optionally implement to ensure front and back stacks are roughly equal in
        // size
        if (3 * front.size() < back.size()) {
            int s = n / 2 - front.size();
            ArrayStack new_front = new ArrayStack();
            ArrayStack new_back = new ArrayStack();
            new_front.addAll(back.subList(0, s));
            new_front.reverse();
            new_front.addAll(front.subList(0, front.size()));
            new_back.addAll(back.subList(s, back.size()));
            front = new_front;
            back = new_back;

        } else if (3 * back.size() < front.size()) {
            int s = front.size() - n / 2;
            ArrayStack new_front = new ArrayStack();
            ArrayStack new_back = new ArrayStack();
            new_front.addAll(front.subList(s, front.size()));
            new_back.addAll(front.subList(0, s));
            new_back.reverse();
            front = new_front;
            back = new_back;
        }
    }

    /**
     * Prints the current state of the DualArrayStack, including underlying arrays
     */
    public void printStack() {
        System.out.println("\n===== DualArrayStack State =====");

        // Logical stack order (what the user sees)
        System.out.print("Logical View (Front→Back): ");
        for (int i = front.size() - 1; i >= 0; i--) {
            System.out.print(front.get(i) + " ");
        }
        for (int i = 0; i < back.size(); i++) {
            System.out.print(back.get(i) + " ");
        }
        System.out.println("\nTotal Size: " + size());

        // Underlying array details (for debugging)
        System.out.println("\n--- Underlying Structure ---");

        // Front array
        System.out.print("Front Array (bottom→top): ");

        for (int i = 0; i < front.size(); i++) {
            if (i < front.size())
                System.out.print("[" + front.get(i) + "]");
            else
                System.out.print("[_]");
        }

        System.out.println();

        // Back array
        System.out.print("Back Array (bottom→top):  ");
        for (int i = 0; i < back.size(); i++) {
            if (i < back.size())
                System.out.print("[" + back.get(i) + "]");
            else
                System.out.print("[_]");
        }

        System.out.println("\n===============================");
    }

    /**
     * Runs an interactive menu for testing the DualArrayStack.
     * Supports add, remove, get, set, printing, and size operations.
     */
    /**
     * Runs an interactive menu for testing DualArrayStack with Scanner and existing
     * stack.
     * Supports add/remove/get/set by index, as well as first/last operations.
     *
     * @param sc    Scanner to read user input
     * @param stack DualArrayStack instance to operate on
     */
    // ==================== DUAL ARRAY STACK ====================
    public static void runDualArrayStack(Scanner sc, DualArrayStack stack) {

        while (true) {
            System.out.println("\n===== DUAL ARRAY STACK MENU =====");
            System.out.println("1. Add First");
            System.out.println("2. Add Last");
            System.out.println("3. Remove First");
            System.out.println("4. Remove Last");
            System.out.println("5. Add at Index");
            System.out.println("6. Remove at Index");
            System.out.println("7. Get Element");
            System.out.println("8. Set Element");
            System.out.println("9. Print Stack");
            System.out.println("10. Show Size");
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

                // ==== FRONT/BACK Operations ====
                case 1 -> {
                    System.out.print("Enter value to add at front: ");
                    Object data = sc.nextLine();
                    stack.addFirst(data);
                    System.out.println("Added at front: " + data);
                }
                case 2 -> {
                    System.out.print("Enter value to add at back: ");
                    Object data = sc.nextLine();
                    stack.addLast(data);
                    System.out.println("Added at back: " + data);
                }
                case 3 -> {
                    if (stack.size() == 0)
                        System.out.println("Stack is empty.");
                    else {
                        Object removed = stack.removeFirst();
                        System.out.println("Removed first: " + removed);
                    }
                }
                case 4 -> {
                    if (stack.size() == 0)
                        System.out.println("Stack is empty.");
                    else {
                        Object removed = stack.removeLast();
                        System.out.println("Removed last: " + removed);
                    }
                }

                // ==== INDEXED OPERATIONS ====
                case 5 -> {
                    System.out.print("Enter index to add at: ");
                    int index = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter value to add: ");
                    Object data = sc.nextLine();
                    if (index < 0 || index > stack.size())
                        System.out.println("Invalid index.");
                    else {
                        stack.add(index, data);
                        System.out.println("Added " + data + " at index " + index);
                    }
                }
                case 6 -> {
                    System.out.print("Enter index to remove: ");
                    int index = Integer.parseInt(sc.nextLine());
                    if (index < 0 || index >= stack.size())
                        System.out.println("Invalid index.");
                    else {
                        stack.remove(index);
                        System.out.println("Removed element at index " + index);
                    }
                }
                case 7 -> {
                    System.out.print("Enter index to get: ");
                    int index = Integer.parseInt(sc.nextLine());
                    if (index < 0 || index >= stack.size())
                        System.out.println("Invalid index.");
                    else
                        System.out.println("Element at index " + index + ": " + stack.get(index));
                }
                case 8 -> {
                    System.out.print("Enter index to set: ");
                    int index = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter new value: ");
                    Object data = sc.nextLine();
                    if (index < 0 || index >= stack.size())
                        System.out.println("Invalid index.");
                    else {
                        stack.set(index, data);
                        System.out.println("Set element at index " + index + " to " + data);
                    }
                }

                // ==== UTILITIES ====
                case 9 -> stack.printStack();
                case 10 -> System.out.println("Current size: " + stack.size());

                // ==== EXIT ====
                case 0 -> {
                    System.out.println("Exiting DualArrayStack menu...");
                    return;
                }

                default -> System.out.println("Invalid option. Please enter 0–10.");
            }
        }
    }
}
