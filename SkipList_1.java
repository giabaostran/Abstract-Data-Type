
import java.util.Random;
import java.util.Stack;

public class SkipList_1 {
    Node sentinel;

    public SkipList_1() {
        // Infinitely small value
        sentinel = new Node(Integer.MIN_VALUE, 1);
    }

    public void add(Integer data) {
        Stack<Node> pred_nodes = findPathToNode(data);
        int height = Node.pick_height();
        Node new_node = new Node(data, height);

        for (int i = 0; i < new_node.next.length; i++) {
            new_node.next[i] = pred_nodes.peek().next[i];
            pred_nodes.pop().next[i] = new_node;
        }

    }

    public Node findPredNode(Integer data) {
        Node agent = sentinel;
        int r = sentinel.next.length - 1;
        while (r >= 0) {
            while (agent.next[r] != null && agent.next[r].data < data)
                agent = agent.next[r];
            r--;
        }
        return agent;
    }

    public Stack<Node> findPathToNode(Integer data) {
        Stack<Node> path = new Stack<>();
        Node agent = sentinel;
        int r = sentinel.next.length - 1;
        while (r >= 0) {
            while (agent.next[r] != null && agent.next[r].data < data)
                agent = agent.next[r];
            path.add(agent);
            r--;
        }
        return path;
    }

    public class Node {

        public Node next[];

        public Integer data;

        public Node(Integer data, int height) {
            next = new Node[1 + height]; // 1 is for the level-0
            this.data = data;
        }

        public static int pick_height() {
            int coin = (new Random()).nextInt();
            int mask = 1;
            int k = 0;
            while ((mask & coin) != 0) {
                mask <<= 1;
                k++;
            }
            return k;
        }
    }
}
