public class SerializerTester {
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree(new Integer[]{1, 2, 1, 7, 5, null, 28, 4});

        System.out.print("BFS on tree: ");
        bt.bfs();
        System.out.println();

        String serialized = bt.serializer(bt.root);
        System.out.println("Serialized binary tree: " + serialized);
        BinaryTree deserialized = new BinaryTree(bt.deserializer(serialized));
        System.out.print("Deserialized binary tree: ");
        deserialized.bfs();

        // Part 3 - Make the Node and BinaryTree class generic to support any data type
    }
}
