
import java.util.ArrayList;
import java.util.List;

public class RootishArrayStack {
    private List<Integer[]> blocks;
    private int n;

    public RootishArrayStack() {
        blocks = new ArrayList<>();
        blocks.add(new Integer[1]);
        n = 0;
    }

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

    public void set(int i, Integer data) {
        if (i < 0 || i > n - 1)
            throw new IndexOutOfBoundsException();
        int b = i2b(i);
        // example i = 4, b = 2 => j = 4 - 3
        int j = i - b * (b + 1) / 2; // 2nd term calc the max index of the structure within the "b" bound
        blocks.get(b)[j] = data;
    }

    public Integer get(int i) {
        if (i < 0 || i > n - 1)
            throw new IndexOutOfBoundsException();
        int b = i2b(i);
        int j = i - b * (b + 1) / 2; // 2nd term calc the max index of the structure within the "b" bound
        return blocks.get(b)[j];
    }

    /*
     * This functions inputs an index i and return the index of the block which
     * that i belongs to. Please don't question the formula
     */
    public int i2b(int i) {
        double db = (-3.0 + Math.sqrt(9 + 8 * i)) / 2.0;
        int b = (int) Math.ceil(db);
        return b;
    }

    public void grow() {
        blocks.add(new Integer[(blocks.size() + 1)]);
    }

    void shrink() {
        int r = blocks.size();
        while (r > 0 && (r - 2) * (r - 1) / 2 >= n) {
            blocks.remove(blocks.size() - 1);
            r--;
        }
    }
}
