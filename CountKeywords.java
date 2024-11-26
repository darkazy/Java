import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords <source-file>");
            return;
        }

        String filename = args[0];
        File file = new File(filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File " + filename + " does not exist or is invalid.");
            return;
        }

        try {
            int keywordCount = countKeywords(file);
            System.out.println("The number of keywords in " + filename + " is " + keywordCount);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    /**
     * Counts the Java keywords in the given source file while ignoring comments and strings.
     *
     * @param file the source file to analyze
     * @return the count of Java keywords in the file
     * @throws IOException if an I/O error occurs
     */
    public static int countKeywords(File file) throws IOException {
        String[] keywordArray = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "for",
            "final", "finally", "float", "goto", "if", "implements", "import", "instanceof",
            "int", "interface", "long", "native", "new", "package", "private", "protected",
            "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized",
            "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "true",
            "false", "null"
        };

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordArray));
        int count = 0;

        try (Scanner input = new Scanner(file)) {
            boolean inBlockComment = false;

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                int i = 0;

                while (i < line.length()) {
                    if (inBlockComment) {
                        int endComment = line.indexOf("*/", i);
                        if (endComment != -1) {
                            inBlockComment = false;
                            i = endComment + 2;
                        } else {
                            break;
                        }
                    }

                    else if (line.startsWith("/*", i)) {
                        inBlockComment = true;
                        i += 2;
                    }

                    else if (line.startsWith("//", i)) {
                        break;
                    }

                    else if (line.charAt(i) == '"') {
                        i++;
                        while (i < line.length() && line.charAt(i) != '"') {
                        
                            if (line.charAt(i) == '\\' && i + 1 < line.length()) {
                                i += 2;
                            } else {
                                i++;
                            }
                        }
                        i++;
                    }

                    else {
                        int start = i;
                        while (i < line.length() && Character.isJavaIdentifierPart(line.charAt(i))) {
                            i++;
                        }
                        if (start < i) {
                            String word = line.substring(start, i);
                            if (keywordSet.contains(word)) {
                                count++;
                            }
                        } else {
                            i++;
                        }
                    }
                }
            }
        }

        return count;
    }
}
