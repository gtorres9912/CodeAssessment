public class Node {
    Node left;
    Node right;
    int num;

    public Node(int num) {
        this.num = num;
    }

    public String toString() {
        return "" + this.num;
    }
}
