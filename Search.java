import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Search {

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsageAndExit();
        }

        boolean ignoreCase = false;
        int index = 0;

        // Optional -i flag test
        if ("-i".equals(args[0])) {
            ignoreCase = true;
            index++;
        }

        if (args.length - index < 2) {
            printUsageAndExit();
        }

        String pattern = args[index];
        String fileName = args[index + 1];

        if (ignoreCase) {
            pattern = pattern.toLowerCase();
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String lineToCheck = ignoreCase ? line.toLowerCase() : line;
                if (lineToCheck.contains(pattern)) {
                    // Print line number and the line
                    System.out.printf("%d: %s%n", lineNumber, line);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file '" + fileName + "': " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printUsageAndExit() {
        System.err.println("Usage:");
        System.err.println("  java Search <pattern> <file>");
        System.err.println("  java Search -i <pattern> <file>   (ignore case)");
        System.exit(1);
    }
}