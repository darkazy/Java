import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class GroupingSymbolChecker {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GroupingSymbolChecker <source-file.java>");
            return;
        }

        String fileName = args[0];
        boolean isBalanced = checkGroupingSymbols(fileName);

        if (isBalanced) {
            System.out.println("The grouping symbols in the file are balanced.");
        } else {
            System.out.println("The grouping symbols in the file are NOT balanced.");
        }
    }

    /**
     * Checks whether the grouping symbols in the given file are balanced.
     *
     * @param fileName The name of the Java source file to check.
     * @return true if the symbols are balanced; false otherwise.
     */
    public static boolean checkGroupingSymbols(String fileName) {
        Stack<Character> stack = new Stack<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                for (int i = 0; i < line.length(); i++) {
                    char current = line.charAt(i);

                    if (current == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/') {
                        break;
                    }

                    if (current == '\"') {
                        i++;
                        while (i < line.length() && line.charAt(i) != '\"') {
                            if (line.charAt(i) == '\\' && i + 1 < line.length()) {
                                i += 2;
                            } else {
                                i++;
                            }
                        }
                        continue;
                    }

                    if (current == '/' && i + 1 < line.length() && line.charAt(i + 1) == '*') {
                        i += 2;
                        while (i + 1 < line.length() && !(line.charAt(i) == '*' && line.charAt(i + 1) == '/')) {
                            i++;
                        }
                        i += 1; // Skip the '/'
                        continue;
                    }

                    if (current == '(' || current == '{' || current == '[') {
                        stack.push(current);
                    }

                    if (current == ')' || current == '}' || current == ']') {
                        if (stack.isEmpty()) {
                            System.out.printf("Unmatched closing '%c' at line %d%n", current, lineNumber);
                            return false;
                        }

                        char lastOpened = stack.pop();
                        if (!isMatchingPair(lastOpened, current)) {
                            System.out.printf("Mismatched symbol '%c' at line %d. Expected '%c' but found '%c'%n",
                                    current, lineNumber, getMatchingClosing(lastOpened), current);
                            return false;
                        }
                    }
                }
            }

            if (!stack.isEmpty()) {
                System.out.println("There are unmatched opening symbols:");
                while (!stack.isEmpty()) {
                    char unmatched = stack.pop();
                    System.out.printf("Unmatched opening '%c'%n", unmatched);
                }
                return false;
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Checks if the opening and closing symbols match.
     *
     * @param open  The opening symbol.
     * @param close The closing symbol.
     * @return true if they match; false otherwise.
     */
    public static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }

    /**
     * Returns the matching closing symbol for a given opening symbol.
     *
     * @param open The opening symbol.
     * @return The corresponding closing symbol.
     */
    public static char getMatchingClosing(char open) {
        switch (open) {
            case '(': return ')';
            case '{': return '}';
            case '[': return ']';
            default: return ' ';
        }
    }
}

