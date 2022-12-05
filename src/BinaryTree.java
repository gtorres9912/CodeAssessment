import java.util.*;

public class BinaryTree implements TreeSerializer {
    protected Node root;

    public BinaryTree(Integer[] list) {
        this.root = createTree(list);
    }

    public BinaryTree(Node node) {
        this.root = node;
    }

    private Node createTree(Integer[] list) {
        Queue<Node> queue = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < list.length; i++) {
            Node current = null;
            if (list[i] != null)
                current = new Node(list[i]);
            if (i == 0) {
                this.root = current;
                queue.add(current);
                continue;
            }
            else {
                if (count == 2) {
                    count = 0;
                    queue.remove();
                }
                while (!queue.isEmpty() && queue.peek() == null) {
                    queue.remove();
                }
                Node temp = queue.peek();
                if (count == 0) {
                    temp.left = current;
                } else {
                    temp.right = current;
                }
                count++;
            }
            queue.add(current);
        }
        return this.root;
    }

    public void bfs() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            System.out.print(current + " ");
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }

    @Override
    public String serializer(Node root) {
        char[] chars = charArray();
        Queue<Node> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            StringBuilder temp = new StringBuilder();
            if (current == null) {
                temp.append("K");
            }
            else {
                int value = current.num;
                if (value < 0) {
                    value *= -1;
                }
                while (value > 0) {
                    temp.append(chars[value % 10]);
                    value /= 10;
                }
                if (current.num == 0) {
                    temp.append("A");
                }
                if (current.num < 0) {
                    temp.append("-");
                }
                queue.add(current.left);
                queue.add(current.right);
            }

            sb.append(temp.reverse());
            if (!queue.isEmpty())
                sb.append(",");
        }
        return sb.toString();
    }

    @Override
    public Node deserializer(String str) {
        String[] tokens = str.split(",");
        Integer[] list = new Integer[tokens.length];
        Map<Character, Integer> map = charsMap();
        for (int i = 0; i < tokens.length; i++) {
            StringBuilder sb = new StringBuilder();
            boolean isNegative = false;
            for (int j = 0; j < tokens[i].length(); j++) {
                if (tokens[i].charAt(j) == '-') {
                    isNegative = true;
                    continue;
                }
                sb.append(map.get(tokens[i].charAt(j)));
            }
            int index = Integer.parseInt(sb.toString());
            if (index == 10 && tokens[i].length() == 1) {
                list[i] = null;
            }
            else {
                list[i] = Integer.parseInt(sb.toString()) * (isNegative ? -1 : 1);
            }
        }
        return createTree(list);
    }

    @Override
    public boolean detectCycle(Node node) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> set = new HashSet<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (set.contains(current)) {
                throw new RuntimeException();
            }
            System.out.print(current + " ");
            if (current.left != null) {
                queue.add(current.left);
            }
            else {
                System.out.print("null ");
            }
            if (current.right != null) {
                queue.add(current.right);
            }
            else {
                System.out.print("null ");
            }
            set.add(current);
        }
        return false;
    }

    private char[] charArray() {
        char[] chars = new char[11];
        char start = 'A';
        for (int i = 0; i < chars.length; i++) {
            chars[i] = start;
            start += 1;
        }
        return chars;
    }

    private Map<Character, Integer> charsMap() {
        Map<Character, Integer> map = new HashMap<>();
        char c = 'A';
        for (int i = 0; i < 11; i++) {
            map.put(c, i);
            c++;
        }
        return map;
    }
}
