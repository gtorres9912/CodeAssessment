import java.util.*;

public class JSONParser {
    public static Map<String, Object> parse(String json) {
        Map<String, Object> map = new HashMap<>();
        for (String s : split(json, ',')) {
            ArrayList<String> keyValue = split(s, ':');
            String key = keyValue.get(0).replace("\"", "").strip();
            //recursion for inner map
            if (keyValue.get(1).strip().charAt(0) == '{') {
                String value = keyValue.get(1).strip();
                Map<String, Object> innerMap = parse(value.substring(1, value.length() - 1));
                map.put(key, innerMap);
            }
            //values that are a list
            else if (keyValue.get(1).strip().charAt(0) == '[') {
                if (containsObject(keyValue.get(1))) {
                    String value = keyValue.get(1);
                    ArrayList<Object> list = new ArrayList<>();
                    for (String object : splitObjects(value.substring(1, value.length() - 1))) {
                        if (object.charAt(0) == '{') {
                            list.add(parse(object.substring(1, object.length() - 1)));
                        }
                        else if (object.equalsIgnoreCase("true")) {
                            list.add(true);
                        }
                        else if (object.equalsIgnoreCase("false")) {
                            list.add(false);
                        }
                        else if (object.equalsIgnoreCase("null")) {
                            list.add(null);
                        }
                        else if (object.charAt(0) == '\"') {
                            list.add(object);
                        }
                        else if (object.contains(".")) {
                            list.add(Double.parseDouble(object));
                        }
                        else {
                            if (object.contains("]")) {
                                list.add(Integer.parseInt(object.substring(object.length() - 1)));
                            }
                            else {
                                list.add(Integer.parseInt(object));
                            }
                        }

                    }
                    map.put(key, list);
                }
                else {
                    String[] values = valuesSubstring(keyValue.get(1).strip()).split(",");
                    ArrayList<Object> list = new ArrayList<>();
                    for (String value : values) {
                        if (value.equalsIgnoreCase("true")) {
                            list.add(true);
                        }
                        else if (value.equalsIgnoreCase("false")) {
                            list.add(false);
                        }
                        else if (value.equalsIgnoreCase("null")) {
                            list.add(null);
                        }
                        else if (value.contains("\"")) {
                            list.add(value);
                        }
                        else if (value.contains(".")) {
                            list.add(Double.parseDouble(value));
                        }
                        else {
                            list.add(Integer.parseInt(value));
                        }
                    }
                    map.put(key, list);
                }
            }
            //Parse values with no inner map
            else {
                String value = keyValue.get(1);
                if (value.contains("\"")) {
                    map.put(key, keyValue.get(1).replace("\"", ""));
                } else if (value.contains(".")) {
                    map.put(key, Double.parseDouble(keyValue.get(1)));
                }
                else if (value.equalsIgnoreCase("true")) {
                    map.put(key, true);
                }
                else if (value.equalsIgnoreCase("false")) {
                    map.put(key, false);
                }
                else if (value.equalsIgnoreCase("null")) {
                    map.put(key, null);
                }
                else {
                    map.put(key, Integer.parseInt(keyValue.get(1)));
                }
            }
        }
        return map;
    }

    private static ArrayList<String> split(String s, char c) {
        ArrayList<String> tokens = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        boolean inQuote = false;
        boolean inList = false;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\"') {
                inQuote = !inQuote;
            }
            else if (s.charAt(i) == '{' && i != 0 && !inQuote) {
                stack.push(s.charAt(i));
            }
            else if (s.charAt(i) == '}' && i != s.length() - 1 && !inQuote) {
                stack.pop();
            }
            else if (s.charAt(i) == '[' && !inQuote && !inList && stack.size() == 0) {
                inList = true;
            }
            else if (s.charAt(i) == ']' && !inQuote && !inList && stack.size() == 0) {
                inList = false;
            }
            if (s.charAt(i) == c && !inQuote && stack.size() == 0 && !inList) {
                tokens.add(s.substring(start, i));
                start = i + 1;
            }
        }
        tokens.add(s.substring(start));
        return tokens;
    }

    private static String valuesSubstring(String values) {
        boolean inQuote = false;
        for (int i = 0; i < values.length(); i++) {
            if (values.charAt(i) == '\"') {
                inQuote = !inQuote;
            }
            else if (values.charAt(i) == ']' && !inQuote) {
                return values.substring(1, i);
            }
        }
        return "";
    }

    private static ArrayList<String> splitObjects(String s) {
        char c = ',';
        ArrayList<String> tokens = new ArrayList<>();
        boolean inQuote = false;
        boolean inList = false;
        boolean inCurleyBracket = false;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\"') {
                inQuote = !inQuote;
            }
            else if (s.charAt(i) == '[' && !inQuote) {
                inList = true;
            }
            else if (s.charAt(i) == ']' && !inQuote) {
                inList = false;
            }
            else if (s.charAt(i) == '{' && !inQuote) {
                inCurleyBracket = true;
            }
            else if (s.charAt(i) == '}' && !inQuote) {
                inCurleyBracket = false;
            }
            if (s.charAt(i) == c && !inQuote && !inList && !inCurleyBracket) {
                tokens.add(s.substring(start, i));
                start = i + 1;
            }
        }
        tokens.add(s.substring(start));
        return tokens;
    }

    private static boolean containsObject(String s) {
        int count = 0;
        boolean inQuote = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\"') {
                inQuote = !inQuote;
            }
            else if ((s.charAt(i) == '{' || s.charAt(i) == '}') && !inQuote) {
                count++;
            }

            if (count == 2) {
                return true;
            }
        }
        return false;
    }
}
