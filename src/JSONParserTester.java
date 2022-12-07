import java.util.*;

public class JSONParserTester {
    public static void main(String[] args) {
        String input = "{\"debug\": \"on\", \"window\" : {\"title\": \"sample\", \"size\": 500}}";
        input = input.replace("\t", "").replace("\n", "").replace(" ", "");
        System.out.println(input);
        Map<String, Object> output = JSONParser.parse(input.substring(1, input.length() - 1));
        System.out.println("\nOutput: \n" + output);
        System.out.println(output.get("debug"));
        System.out.println(output.get("window"));

        if (!output.get("debug").equals("on")) throw new AssertionError();
        if (!((Map<String, Object>) output.get("window")).get("title").equals("sample")) throw new AssertionError();
        if (!((Map<String, Object>) output.get("window")).get("size").equals(500)) throw new AssertionError();
    }
}