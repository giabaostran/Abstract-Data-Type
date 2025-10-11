import java.util.Scanner;

/**
 * ArrayQueue implements a double-ended queue (deque) using a dynamic circular
 * array.
 * It supports adding/removing elements from both the front and the back,
 * automatic resizing, and provides a simple interactive menu for testing.
 */
public class ArrayQueue {

    private Object[] arr; // The underlying array that stores the queue elements.
    private int n; // The current number of elements in the queue.
    private int offset; // The index of the front element in the circular array.

    /**
     * Constructs an empty ArrayQueue with initial capacity of 1.
     */
    public ArrayQueue() {
        arr = new Object[1];
        n = offset = 0;
    }

    /**
     * Resizes the underlying array when it becomes full or too sparse.
     * Doubles the capacity when expanding and resets offset to 0.
     */
    public void resize() {
        Object[] new_array = new Object[Math.max(1, n * 2)];
        for (int i = 0; i < n; i++)
            new_array[i] = arr[(offset + i) % arr.length];
        arr = new_array;
        offset = 0;
    }

    /**
     * Adds an element to the back of the queue.
     * 
     * @param data The element to add.
     */
    public void addLast(Object data) {
        if (n + 1 > arr.length)
            resize();
        arr[(offset + n) % arr.length] = data;
        n++;
    }

    /**
     * Adds an element to the front of the queue.
     * 
     * @param data The element to add.
     */
    public void addFirst(Object data) {
        if (n + 1 > arr.length)
            resize();
        offset = offset == 0 ? arr.length - 1 : offset - 1;
        arr[offset] = data;
        n++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     * 
     * @return The element removed, or null if the queue is empty.
     */
    public Object removeFirst() {
        if (isEmpty())
            return null;
        Object val = arr[offset];
        offset = (offset + 1) % arr.length;
        n--;
        return val;
    }

    /**
     * Removes and returns the element at the back of the queue.
     * 
     * @return The element removed, or null if the queue is empty.
     */
    public Object removeLast() {
        if (isEmpty())
            return null;
        Object val = arr[(offset + n - 1) % arr.length];
        n--;
        if (n <= arr.length / 3) // shrink if sparse
            resize();
        return val;
    }

    /**
     * Returns the element at the specified index without removing it.
     * 
     * @param index The position (0 = front) of the element.
     * @return The element at the given index.
     * @throws IndexOutOfBoundsException if index < 0 or >= size.
     */
    public Object get(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException();
        return arr[(offset + index) % arr.length];
    }

    /**
     * Returns the number of elements in the queue.
     * 
     * @return Current size of the queue.
     */
    public int size() {
        return n;
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        n = 0;
        offset = 0;
    }

    /**
     * Checks if the queue is empty.
     * 
     * @return True if the queue contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Prints the current state of the queue, including elements from front to back,
     * the current size, offset, and the underlying array.
     */
    public void printQueue() {
        System.out.println("\n=== Queue State ===");

        if (isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            System.out.print("Queue elements (front → back): ");
            for (int i = 0; i < n; i++) {
                System.out.print(get(i) + " ");
            }
            System.out.println();
        }

        System.out.println("Size (n): " + n);
        System.out.println("Offset: " + offset);

        System.out.print("Underlying array: [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] != null ? arr[i] : "_");
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    /**
     * Static method to run an interactive menu for testing the ArrayQueue.
     * Allows adding/removing elements, printing, clearing, and checking size.
     */
    public static void runArrayQueue(Scanner sc, ArrayQueue queue) {
        while (true) {
            System.out.println("\n===== ARRAY QUEUE MENU =====");
            System.out.println("1. Add First");
            System.out.println("2. Add Last");
            System.out.println("3. Remove First");
            System.out.println("4. Remove Last");
            System.out.println("5. Print Queue (horizontal)");
            System.out.println("6. Show Size");
            System.out.println("7. Clear Queue");
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
                    System.out.print("Enter value to add first: ");
                    Object data = sc.nextLine();
                    queue.addFirst(data);
                    System.out.println("Added at front: " + data);
                }
                case 2 -> {
                    System.out.print("Enter value to add last: ");
                    Object data = sc.nextLine();
                    queue.addLast(data);
                    System.out.println("Added at back: " + data);
                }
                case 3 -> {
                    Object removed = queue.removeFirst();
                    System.out.println(removed != null ? "Removed first: " + removed : "Queue is empty.");
                }
                case 4 -> {
                    Object removed = queue.removeLast();
                    System.out.println(removed != null ? "Removed last: " + removed : "Queue is empty.");
                }
                case 5 -> queue.printQueue();
                case 6 -> System.out.println("Current size: " + queue.size());
                case 7 -> {
                    queue.clear();
                    System.out.println("Queue cleared.");
                }
                case 8 -> System.out.println(queue.isEmpty() ? "Queue is empty." : "Queue is not empty.");
                case 0 -> {
                    System.out.println("Exiting ArrayQueue menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

}
