
import java.util.LinkedList;
import java.util.Random;

public class SkipList {
    Node sentinel;
    int height;
    int n;

    public SkipList() {
        sentinel = new Node();
        height = 0;
        n = 0;
    }

    public Node findPredNode(Integer data) {
        Node agent = sentinel;
        for (int r = sentinel.next.length - 1; r >= 0; r--)
            while (agent.next[r] != null && Integer.compare(data, agent.next[r].data) > 0)
                agent = agent.next[r];
        return agent;
    }

    public Integer find(Integer data) {
        Node node = findPredNode(data);
        return node.next[0] == null ? null : node.next[0].data;
    }

    public boolean remove(Integer data) {
        Node agent = sentinel;
        int compare_result = 0;
        boolean removed = false;
        for (int r = sentinel.next.length - 1; r >= 0; r--) {
            while (agent.next[r] != null && (compare_result = Integer.compare(data, agent.next[r].data)) > 0)
                agent = agent.next[r];
            if (agent.next[r] != null && compare_result == 0) {
                removed = true;
                agent.next[r] = agent.next[r].next[r];
                if (agent == sentinel && agent.next[r] == null)
                    height--;
            }
        }
        if (removed)
            n--;
        return removed;
    }

    public boolean add(Integer data) {
        Node agent = sentinel;
        LinkedList<Node> travelled_path = new LinkedList<>();
        int compare_result = 0;

        for (int r = height - 1; r >= 0; r--) {
            while (agent.next[r] != null && (compare_result = Integer.compare(data, agent.next[r].data)) > 0)
                agent = agent.next[r];
            if (agent.next[r] != null && compare_result == 0)
                return false; // Already exists
            travelled_path.add(agent);
        }

        Node new_node = new Node(data, flip_a_coin());

        for (int i = 0; i <= new_node.getHeight(); i++) {
            new_node.next[i] = travelled_path.getLast().next[i];
            travelled_path.pop().next[i] = agent;
            if (i > height)
                ++height;
        }
        n++;
        return true;
    }

    public class Node {
        final static int MAX_HEIGHT = 16;

        public Node next[];

        public Integer data;

        public Node(Integer data, int height) {
            next = new Node[1 + Math.min(height, MAX_HEIGHT)]; // 1 is for the level-0
            this.data = data;
        }

        public Node() {
            next = new Node[1 + MAX_HEIGHT]; // 1 is for the level-0
            this.data = Integer.MIN_VALUE;
        }

        public int getHeight() {
            return next.length - 1;
        }
    }

    public static int flip_a_coin() {
        int coin = (new Random()).nextInt();
        int mask = 1;
        int head_count = 0;
        while ((mask & coin) != 0) {
            mask <<= 1;
            head_count++;
        }
        return head_count;
    }
}
