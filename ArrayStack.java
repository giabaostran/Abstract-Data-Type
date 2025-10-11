
public class ArrayStack {
    private Object[] list;
    private int n;

    public ArrayStack() {
        list = new Object[1];
        n = 0;
    }

    public void resize() {
        Object[] new_array = new Object[n * 2];
        System.arraycopy(list, 0, new_array, 0, n);
        list = new_array;
    }

    public void add(int index, Object data) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException();
        if (n + 1 > list.length)
            resize();
        for (int i = n; i > index; i--)
            list[i] = list[i - 1];

        list[index] = data;
        ++n;
    }

    public void add(Object data) {
        add(n, data);
    }

    public Object remove(int index) {
        if (index < 0 || index > n)
            throw new IndexOutOfBoundsException();

        Object removed_obj = list[index];
        for (int i = index; i < n - 1; i++)
            list[i] = list[i + 1];
        n--;
        if (n <= list.length / 3)
            resize();

        return removed_obj;
    }

    public Object set(int index, Object data) {
        if (index < 0 || index > n - 1)
            throw new IndexOutOfBoundsException();
        Object y = list[index];
        list[index] = data;
        return y;
    }

    public Object get(int index) {
        if (index < 0 || index > n - 1)
            throw new IndexOutOfBoundsException();
        return list[index];
    }

    public boolean contains(Object data) {
        for (Object obj : list)
            if (obj.equals(data))
                return true;
        return false;
    }

    public int size() {
        return n;
    }

    public void clear() {
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

}