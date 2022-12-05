import java.util.Map;
public class JSONParserTester {
    public static void main(String[] args) {
        String input = "{\"debug\": \"on\", \"window\" : {\"title\": \"sample\", \"size\": 500}}";
        input = input.strip();
        System.out.println(input);
        System.out.println();
        Map<String, Object> output = JSONParser.parse(input.substring(1, input.length() - 1));
        System.out.println(output);
        System.out.println(output.get("debug"));
        System.out.println(output.get("window"));
        System.out.println(output.get("window").getClass());

        String s = (String) output.get("window");
        System.out.println(s);


        if (!output.get("debug").equals("on")) throw new AssertionError();
//        assert (Map<String, Object>(output.get("window")).get("title").equals("sample");
//        assert (Map<String, Object>(output.get("window")).get("size").equals(500);
    }
}