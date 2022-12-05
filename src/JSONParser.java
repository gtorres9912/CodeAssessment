import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class JSONParser {
    public static Map<String, Object> parse(String json) {
        Map<String, Object> map = new HashMap<>();
        for (String s : split(json, ',')) {
            ArrayList<String> keyValue = split(s, ':');
            String key = keyValue.get(0).replace("\"", "").strip();
            if (keyValue.get(1).charAt(0) == '{') {
                String value = keyValue.get(1).replace("\"", "").strip();
                Map<String, Object> innerMap = parse(value.substring(0, value.length() - 1));
                map.put(key, innerMap);
            }
            else {
                Object o = (Object) keyValue.get(1).replace("\"", "").strip();
                map.put(key, o);
            }
        }
        return map;
    }


    private static ArrayList<String> split(String s, char c) {
        ArrayList<String> tokens = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        boolean inQuote = false;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\"') {
                inQuote = !inQuote;
            }
            if (s.charAt(i) == '{' && i > 0) {
                stack.push(s.charAt(i));
            }
            if (s.charAt(i) == '}' && i < s.length() - 1) {
                stack.pop();
            }
            if (s.charAt(i) == c && !inQuote && stack.size() == 0) {
                tokens.add(s.substring(start, i));
                start = i + 1;
            }
        }
        tokens.add(s.substring(start));
        return tokens;
    }
}
