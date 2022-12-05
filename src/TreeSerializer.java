public interface TreeSerializer {
    String serializer(Node root);

    Node deserializer(String str);

    boolean detectCycle(Node root);
}
