public class ArrayDeque {

    private Object[] list;
    private int n;
    private int offset;

    public ArrayDeque() {
        list = new Object[1];
        n = offset = 0;
    }

    public void resize() {
        Object[] new_array = new Object[n * 2];
        for (int i = 0; i < n; i++)
            new_array[i] = list[(offset + n + i) % list.length];
        list = new_array;
        offset = 0;
    }

    public void addLast(Object data) {
        if (n + 1 > list.length)
            resize();
        list[(offset + n) % list.length] = data;
        n++;
    }

    public void addFirst(Object data) {
        if (n + 1 > list.length)
            resize();
        offset = offset == 0 ? list.length - 1 : offset - 1;
        list[offset] = data;
        n++;
    }

    public void add(int index, Object data) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException();
        if (n + 1 > list.length)
            resize();
        if (index < n / 2) {
            offset = offset == 0 ? list.length - 1 : offset - 1;
            for (int k = 0; k < index; k++)
                list[(offset + k) % list.length] = list[(k + offset + 1) % list.length];

        } else {
            for (int k = n; k < list.length; k++)
                list[(offset + k) % list.length] = list[(k + offset + -1) % list.length];
        }
        list[index] = data;
        n++;
    }

    public void removeFirst() {
        offset = (offset + 1) % list.length;
        n--;
    }

    public void removeLast() {
        n--;
    }

    public void remove(int index) {
        if (index < n / 2) {
            for (int k = index; k > 0; k++)
                list[(k + offset) % list.length] = list[(k + offset - 1) % list.length];
            offset++;
        } else {
            for (int k = index; k < n; k++) {
                list[(k + offset) % list.length] = list[(k + offset + 1) % list.length];
            }
        }
        n--;
    }

    public Object get(int index) {
        return list[(offset + list.length + index) % list.length];
    }

    public int size() {
        return n;
    }

    public void clear() {
        n = 0;
        offset = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

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
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null)
                System.out.print(list[i]);
            else
                System.out.print("_");
            if (i < list.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

}