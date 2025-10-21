import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RootishArrayStack implements a dynamic list using a sequence of
 * progressively larger blocks (sizes 1, 2, 3, ...).
 * 
 * Total wasted space = O(√n).
 * Supports add, remove, get, set, and interactive testing.
 */
public class RootishArrayStack {
    private List<Integer[]> blocks; // list of blocks
    private int n; // total number of elements

    /** Constructs an empty RootishArrayStack with one block of size 1. */
    public RootishArrayStack() {
        blocks = new ArrayList<>();
        blocks.add(new Integer[1]);
        n = 0;
    }

    /** Inserts new_item at index i, shifting later elements right. */
    public boolean add(int i, Integer new_item) {
        if (i < 0 || i > n)
            throw new IndexOutOfBoundsException();
        int r = blocks.size();
        if (r * (r + 1) / 2 < n + 1)
            grow();

        for (int j = n; j > i; j--)
            set(j, get(j - 1));

        set(i, new_item);
        n++;
        return true;
    }

    /** Removes and returns element at index i, shifting left. */
    public Integer remove(int i) {
        if (i < 0 || i > n - 1)
            throw new IndexOutOfBoundsException();

        Integer removed_item = get(i);
        for (int j = i; j < n - 1; j++)
            set(j, get(j + 1));
        n--;

        int r = blocks.size();
        if ((r - 2) * (r - 1) / 2 >= n)
            shrink();
        return removed_item;
    }

    /** Replaces element at index i with data. */
    public void set(int i, Integer data) {
        if (i < 0 || i > n - 1)
            throw new IndexOutOfBoundsException();
        int b = i2b(i);
        int j = i - b * (b + 1) / 2;
        blocks.get(b)[j] = data;
    }

    /** Returns element at index i. */
    public Integer get(int i) {
        if (i < 0 || i > n - 1)
            throw new IndexOutOfBoundsException();
        int b = i2b(i);
        int j = i - b * (b + 1) / 2;
        return blocks.get(b)[j];
    }

    /** Maps a global index i to its block index. */
    public int i2b(int i) {
        double db = (-3.0 + Math.sqrt(9 + 8 * i)) / 2.0;
        return (int) Math.ceil(db);
    }

    /** Adds a new block of size (current + 1). */
    public void grow() {
        blocks.add(new Integer[blocks.size() + 1]);
    }

    /** Removes unnecessary trailing blocks when underused. */
    void shrink() {
        int r = blocks.size();
        while (r > 0 && (r - 2) * (r - 1) / 2 >= n) {
            blocks.remove(blocks.size() - 1);
            r--;
        }
    }

    /** Prints both linear view and block view of the stack. */
    public void print() {
        System.out.println("\n--- RootishArrayStack State ---");
        System.out.println("n = " + n + ", blocks = " + blocks.size());
        System.out.print("Linear view: [");
        for (int i = 0; i < n; i++) {
            System.out.print(get(i));
            if (i < n - 1)
                System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("Block structure:");
        for (int b = 0; b < blocks.size(); b++) {
            System.out.print("  Block " + b + ": [");
            Integer[] block = blocks.get(b);
            for (int j = 0; j < block.length; j++) {
                System.out.print(block[j] != null ? block[j] : "_");
                if (j < block.length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
        }
        System.out.println("-------------------------------\n");
    }

    /** Returns number of stored elements. */
    public int size() {
        return n;
    }

    /** Clears all data. */
    public void clear() {
        blocks.clear();
        blocks.add(new Integer[1]);
        n = 0;
    }

    /** True if stack is empty. */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Interactive menu to test RootishArrayStack operations.
     */
    public static void runRootishArrayStack(Scanner sc, RootishArrayStack stack) {
        while (true) {
            System.out.println("\n===== ROOTISH ARRAY STACK MENU =====");
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. Get");
            System.out.println("4. Set");
            System.out.println("5. Print");
            System.out.println("6. Size");
            System.out.println("7. Clear");
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

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter index to insert at: ");
                        int index = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter integer value: ");
                        int val = Integer.parseInt(sc.nextLine());
                        stack.add(index, val);
                        System.out.println("Added " + val + " at index " + index);
                    }
                    case 2 -> {
                        System.out.print("Enter index to remove: ");
                        int index = Integer.parseInt(sc.nextLine());
                        System.out.println("Removed value: " + stack.remove(index));
                    }
                    case 3 -> {
                        System.out.print("Enter index to get: ");
                        int index = Integer.parseInt(sc.nextLine());
                        System.out.println("Value at index " + index + " = " + stack.get(index));
                    }
                    case 4 -> {
                        System.out.print("Enter index to set: ");
                        int index = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter new value: ");
                        int val = Integer.parseInt(sc.nextLine());
                        stack.set(index, val);
                        System.out.println("Set index " + index + " to " + val);
                    }
                    case 5 -> stack.print();
                    case 6 -> System.out.println("Current size: " + stack.size());
                    case 7 -> {
                        stack.clear();
                        System.out.println("Stack cleared.");
                    }
                    case 8 -> System.out.println(stack.isEmpty() ? "Stack is empty." : "Stack is not empty.");
                    case 0 -> {
                        System.out.println("Exiting RootishArrayStack menu...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
