import java.util.Scanner;

/**
 * ArrayStack - A simple dynamic stack implementation using an array as backing
 * storage.
 * Supports basic operations like push, pop, peek, and resizing.
 */
public class ArrayStack implements ListInterface {
    private Object[] list; // Underlying array for stack elements
    private int n; // Current number of elements in the stack

    /**
     * Default constructor initializes stack with capacity 1.
     */
    public ArrayStack() {
        list = new Object[1];
        n = 0;
    }

    /**
     * Doubles the current array size when capacity is exceeded or shrinks it when
     * necessary.
     * Ensures capacity never falls below 1.
     */
    private void resize() {
        int newSize = Math.max(1, n * 2); // Avoids creating a zero-length array
        Object[] new_array = new Object[newSize];
        // Copy existing elements into the new array
        System.arraycopy(list, 0, new_array, 0, n);
        list = new_array;
    }

    /**
     * Adds an element at the specified index.
     * Shifts existing elements to the right if needed.
     *
     * @param index Position to insert element
     * @param data  Element to add
     */
    @Override
    public void add(int index, Object data) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);

        // Resize if full
        if (n + 1 > list.length)
            resize();

        // Shift elements to the right
        for (int i = n; i > index; i--)
            list[i] = list[i - 1];

        // Insert new element
        list[index] = data;
        n++;
    }

    /**
     * Adds an element to the top of the stack (end of the array).
     *
     * @param data Element to push
     */
    @Override
    public void add(Object data) {
        add(n, data);
    }

    /**
     * Removes an element at a specified index and shifts remaining elements left.
     *
     * @param index Position to remove
     * @return Removed element
     */
    public Object remove(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);

        Object removed_obj = list[index];

        // Shift elements left to fill the gap
        for (int i = index; i < n - 1; i++)
            list[i] = list[i + 1];
        n--;

        // Optional shrink: resize if the stack becomes sparse
        if (n <= list.length / 3)
            resize();

        return removed_obj;
    }

    /**
     * Replaces an element at a specific index and returns the old one.
     *
     * @param index Position to replace
     * @param data  New data
     * @return Old element at that position
     */
    @Override

    public Object set(int index, Object data) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);

        Object old = list[index];
        list[index] = data;
        return old;
    }

    /**
     * Retrieves an element at the specified index without removing it.
     *
     * @param index Position to access
     * @return Element at that position
     */
    @Override
    public Object get(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return list[index];
    }

    /**
     * Checks if the stack contains a specific element.
     *
     * @param data Element to find
     * @return True if found, false otherwise
     */
    @Override
    public boolean contains(Object data) {
        for (int i = 0; i < n; i++)
            if (list[i].equals(data))
                return true;
        return false;
    }

    /**
     * Returns the current number of elements in the stack.
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * Clears all elements from the stack.
     */
    @Override
    public void clear() {
        n = 0;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return True if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Prints the stack horizontally for visualization.
     * Bottom element is on the left, top on the right.
     */
    public void printStack() {
        System.out.print("Stack (bottom → top): ");
        for (int i = 0; i < n; i++) {
            System.out.print("[" + list[i] + "]");
            if (i < n - 1)
                System.out.print(" ");
        }
        System.out.println("  <-- horizontal view");
        System.out.println("Size: " + n);
    }

    // ==================== ARRAY STACK ====================
    public static void runArrayStack(ArrayStack stack) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== ARRAY STACK MENU =====");
            System.out.println("1. Push element");
            System.out.println("2. Pop element");
            System.out.println("3. Peek (top element)");
            System.out.println("4. Print Stack (horizontal)");
            System.out.println("5. Get Size");
            System.out.println("6. Clear Stack");
            System.out.println("7. Check if Empty");
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
                    System.out.print("Enter element to push: ");
                    Object data = sc.nextLine();
                    stack.add(data);
                    System.out.println("Pushed: " + data);
                }
                case 2 -> {
                    if (stack.isEmpty())
                        System.out.println("Stack is empty.");
                    else
                        System.out.println("Popped: " + stack.remove(stack.size() - 1));
                }
                case 3 -> {
                    if (stack.isEmpty())
                        System.out.println("Stack is empty.");
                    else
                        System.out.println("Top element: " + stack.get(stack.size() - 1));
                }
                case 4 -> {
                    if (stack.isEmpty())
                        System.out.println("Stack is empty.");
                    else {
                        System.out.print("Stack (bottom → top): ");
                        for (int i = 0; i < stack.size(); i++) {
                            System.out.print("[" + stack.get(i) + "]");
                            if (i < stack.size() - 1)
                                System.out.print(" ");
                        }
                        System.out.println("  <-- horizontal view");
                    }
                    System.out.println("Size: " + stack.size());
                }
                case 5 -> System.out.println("Stack size: " + stack.size());
                case 6 -> {
                    stack.clear();
                    System.out.println("Stack cleared.");
                }
                case 7 -> System.out.println(stack.isEmpty() ? "Stack is empty." : "Stack is not empty.");
                case 0 -> {
                    System.out.println("Exiting ArrayStack test...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
