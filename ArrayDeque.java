import java.util.Scanner;

/**
 * ArrayDeque implements a double-ended queue (deque) using a dynamic circular
 * array.
 * It supports adding and removing elements from both ends, random access by
 * index,
 * insertion/removal at specific indices, and automatic resizing.
 */
public class ArrayDeque {

    private Object[] array; // The underlying array that stores the deque elements.

    private int n; // The current number of elements in the deque.

    private int offset; // The index of the front element in the circular array.

    /**
     * Constructs an empty ArrayDeque with an initial capacity of 1.
     */
    public ArrayDeque() {
        array = new Object[1];
        n = offset = 0;
    }

    /**
     * Resizes the underlying array when it becomes full or too sparse.
     * Doubles the capacity when expanding, and resets offset to 0.
     * Called automatically by add/remove operations when needed.
     */
    public void resize() {
        Object[] new_array = new Object[Math.max(1, n * 2)];
        for (int i = 0; i < n; i++)
            new_array[i] = array[(offset + i) % array.length];
        array = new_array;
        offset = 0;
    }

    /**
     * Adds an element to the back of the deque.
     *
     * @param data The element to add.
     */
    public void addLast(Object data) {
        if (n + 1 > array.length)
            resize();
        array[(offset + n) % array.length] = data;
        n++;
    }

    /**
     * Adds an element to the front of the deque.
     *
     * @param data The element to add.
     */
    public void addFirst(Object data) {
        if (n + 1 > array.length)
            resize();
        offset = offset == 0 ? array.length - 1 : offset - 1;
        array[offset] = data;
        n++;
    }

    /**
     * Inserts an element at a specific index.
     *
     * @param index Position to insert (0 = front, n = back)
     * @param data  The element to insert
     * @throws IndexOutOfBoundsException if index < 0 or index > n
     */
    public void add(int index, Object data) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException();
        if (n + 1 > array.length)
            resize();

        if (index < n / 2) {
            // Shift elements toward front
            offset = offset == 0 ? array.length - 1 : offset - 1;
            for (int k = 0; k < index; k++)
                array[(offset + k) % array.length] = array[(k + offset + 1) % array.length];
        } else {
            // Shift elements toward back
            for (int k = n; k > index; k--)
                array[(offset + k) % array.length] = array[(k + offset - 1) % array.length];
        }
        array[(offset + index) % array.length] = data;
        n++;
    }

    /**
     * Removes the element at the front of the deque.
     * Automatically shrinks the array if it becomes sparse.
     */
    public void removeFirst() {
        if (n == 0)
            return;
        offset = (offset + 1) % array.length;
        n--;
        if (n <= array.length / 3)
            resize();
    }

    /**
     * Removes the element at the back of the deque.
     * Automatically shrinks the array if it becomes sparse.
     */
    public void removeLast() {
        if (n == 0)
            return;
        n--;
        if (n <= array.length / 3)
            resize();
    }

    /**
     * Removes the element at a specific index.
     * Shifts elements to fill the gap and resizes if necessary.
     *
     * @param index The index of the element to remove
     */
    public void remove(int index) {
        if (index < 0 || index >= n)
            return;

        if (index < n / 2) {
            for (int k = index; k > 0; k--)
                array[(k + offset) % array.length] = array[(k + offset - 1) % array.length];
            offset++;
        } else {
            for (int k = index; k < n - 1; k++)
                array[(k + offset) % array.length] = array[(k + offset + 1) % array.length];
        }
        n--;
        if (n <= array.length / 3)
            resize();
    }

    /**
     * Returns the element at the specified index without removing it.
     *
     * @param index Position of the element (0 = front)
     * @return The element at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= n
     */
    public Object get(int index) {
        if (index < 0 || index >= n)
            throw new IndexOutOfBoundsException();
        return array[(offset + index) % array.length];
    }

    /**
     * Returns the current number of elements in the deque.
     *
     * @return The size of the deque
     */
    public int size() {
        return n;
    }

    /**
     * Removes all elements from the deque and resets offset.
     */
    public void clear() {
        n = 0;
        offset = 0;
    }

    /**
     * Checks if the deque is empty.
     *
     * @return True if the deque contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Prints the current state of the deque, including elements, size,
     * offset, and the underlying array.
     */
    public void printDeque() {
        System.out.println("\n=== Deque State ===");
        if (isEmpty()) {
            System.out.println("Deque is empty.");
        } else {
            System.out.print("Deque elements (front → back): ");
            for (int i = 0; i < n; i++)
                System.out.print(get(i) + " ");
            System.out.println();
        }
        System.out.println("Size: " + n);
        System.out.println("Offset: " + offset);
        System.out.print("Underlying array: [");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] != null ? array[i] : "_");
            if (i < array.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    /**
     * Runs an interactive menu to test ArrayDeque operations.
     * Users can add/remove elements from front, back, or by index,
     * print the deque, check size, clear it, or exit.
     */
    public static void runArrayDeque(ArrayDeque deque) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== ARRAY DEQUE MENU =====");
            System.out.println("1. Add First");
            System.out.println("2. Add Last");
            System.out.println("3. Add at Index");
            System.out.println("4. Remove First");
            System.out.println("5. Remove Last");
            System.out.println("6. Remove at Index");
            System.out.println("7. Print Deque");
            System.out.println("8. Show Size");
            System.out.println("9. Clear Deque");
            System.out.println("10. Check if Empty");
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
                    deque.addFirst(data);
                    System.out.println("Added at front: " + data);
                }
                case 2 -> {
                    System.out.print("Enter value to add last: ");
                    Object data = sc.nextLine();
                    deque.addLast(data);
                    System.out.println("Added at back: " + data);
                }
                case 3 -> {
                    System.out.print("Enter index to insert at: ");
                    int index = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter value: ");
                    Object data = sc.nextLine();
                    try {
                        deque.add(index, data);
                        System.out.println("Added at index " + index + ": " + data);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid index.");
                    }
                }
                case 4 -> {
                    if (deque.isEmpty())
                        System.out.println("Deque is empty.");
                    else {
                        deque.removeFirst();
                        System.out.println("Removed first element.");
                    }
                }
                case 5 -> {
                    if (deque.isEmpty())
                        System.out.println("Deque is empty.");
                    else {
                        deque.removeLast();
                        System.out.println("Removed last element.");
                    }
                }
                case 6 -> {
                    System.out.print("Enter index to remove: ");
                    int index = Integer.parseInt(sc.nextLine());
                    if (index < 0 || index >= deque.size()) {
                        System.out.println("Invalid index.");
                    } else {
                        deque.remove(index);
                        System.out.println("Removed element at index " + index);
                    }
                }
                case 7 -> deque.printDeque();
                case 8 -> System.out.println("Current size: " + deque.size());
                case 9 -> {
                    deque.clear();
                    System.out.println("Deque cleared.");
                }
                case 10 -> System.out.println(deque.isEmpty() ? "Deque is empty." : "Deque is not empty.");
                case 0 -> {
                    sc.close();
                    System.out.println("Exiting ArrayDeque menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
